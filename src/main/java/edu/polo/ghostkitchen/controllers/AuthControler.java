package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.dto.RegisterDeliveryDto;
import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import edu.polo.ghostkitchen.dto.RegisterDto;
import edu.polo.ghostkitchen.dto.RegisterKitchenDto;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthControler {

    @Autowired
    private BCryptPasswordEncoder codificator;

    @Autowired
    private GhostsRepository userRepository;

    @Autowired
    private RecaptchaService recaptchaService;

    @Autowired
    private GhostService ghostService;

    @GetMapping("/login")
    public ModelAndView showLoginForm(Model model,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout) {

        System.out.println("🔥🔥🔥🔥🔥🔥🔥🔥 aca entro");
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Iniciar sesión");
        maw.addObject("vista", "auth/login");
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return maw;
    }

    @GetMapping({"/loginSuccess"})
    public RedirectView logincheck() {
        System.out.println("🔥🔥🔥🔥🔥🔥🔥🔥 loginSuccess");
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
        return maw;
    }

    @PostMapping("/register")
    public ModelAndView registrar(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, @Valid RegisterDto registerDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {
        // ... Validación de reCAPTCHA y otros controles de errores ...
        String ip = request.getRemoteAddr();

        String captchaVerifyMessage = recaptchaService.verifyRecaptcha(ip, recaptchaResponse);
        if (!"".equals(captchaVerifyMessage)) {
            br.rejectValue("recaptcha", "recaptcha", captchaVerifyMessage);
        }

        if (br.hasErrors()) {
            return this.register(registerDto);
        }
        // Registrar al usuario y guardar en la base de datos
        Ghosts u = new Ghosts();
        u.setAddress(registerDto.getAddress());
        u.setName(registerDto.getName());
        u.setEmail(registerDto.getEmail());
        u.setPassword(codificator.encode(registerDto.getPassword()));
        if (userRepository.existsByRole(Ghosts.GhostRole.Administrador)) {
            u.setRole(Ghosts.GhostRole.Cliente);
        } else {
            u.setRole(Ghosts.GhostRole.Administrador);
        }

        userRepository.save(u);

        // Redirigir al usuario a la página de inicio de sesión
        return new ModelAndView("redirect:/login");
    }

}
