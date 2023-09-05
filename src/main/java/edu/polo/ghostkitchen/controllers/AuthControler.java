package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import edu.polo.ghostkitchen.dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Date;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;

@Controller
public class AuthControler {

    @Autowired
    private BCryptPasswordEncoder codificator;

    @Autowired
    private GhostsRepository userRepository;

    @Autowired
    private RecaptchaService recaptchaService;

    @Autowired
    private CategoryService categoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm(HttpSession session,Model model,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout) {

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Iniciar sesión");
        maw.addObject("vista", "auth/login");
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        maw.addObject("allcategory", categoryService.getAll());
        
      
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Ghosts user = userRepository.findByUsername(username);
//        session.setAttribute("usuario", user);
//          System.out.println(user + "Y ESTO ES SESION: " + session);
//    
      
        return maw;
    }

    @GetMapping({"/loginSuccess"})
    public RedirectView logincheck() {
        return new RedirectView("/");
    }

    /* @GetMapping({"/home"})
    public RedirectView login() {
        return new RedirectView("/");
    }*/
    @GetMapping("/register")
    public ModelAndView register(RegisterDto registerDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Registrarse");
        maw.addObject("vista", "auth/register");
        maw.addObject("registerDto", registerDto);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

    @PostMapping("/register")
    public ModelAndView registrar(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, Date birthday, @Valid RegisterDto registerDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {
        // ... Validación de reCAPTCHA y otros controles de errores ...
        String ip = request.getRemoteAddr();

        String captchaVerifyMessage = recaptchaService.verifyRecaptcha(ip, recaptchaResponse);
        if (!"".equals(captchaVerifyMessage)) {
            br.rejectValue("recaptcha", "recaptcha", captchaVerifyMessage);
        }

        if (br.hasErrors()) {
            return this.register(registerDto);
        }

        Ghosts u = new Ghosts();
        u.setAddress(registerDto.getAddress());
        u.setName(registerDto.getName());
        u.setEmail(registerDto.getEmail());
        u.setPassword(codificator.encode(registerDto.getPassword()));
        u.setBirthday(registerDto.getBirthday());
        if (userRepository.existsByRole(Ghosts.GhostRole.Administrador)) {
            u.setRole(Ghosts.GhostRole.Cliente);
        } else {
            u.setRole(Ghosts.GhostRole.Administrador);
        }
        userRepository.save(u);
        ra.addFlashAttribute("message", "Usuario creado exitosamente");
        return new ModelAndView("redirect:/login");
    }

}
