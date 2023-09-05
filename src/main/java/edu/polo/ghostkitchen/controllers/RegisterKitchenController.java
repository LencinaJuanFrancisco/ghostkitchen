package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.services.*;
import edu.polo.ghostkitchen.dto.RegisterDto;
import edu.polo.ghostkitchen.dto.RegisterKitchenDto;
import edu.polo.ghostkitchen.repositories.ChefRepository;
import edu.polo.ghostkitchen.repositories.GhostsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;

@Controller
public class RegisterKitchenController {

    @Autowired
    private BCryptPasswordEncoder codificator;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private GhostsRepository userRepository;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/registerKitchen")
    public ModelAndView registerKitchen(RegisterKitchenDto registerDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Registrarse");
        maw.addObject("vista", "auth/registerKitchen");
        maw.addObject("registerKitchenDto", registerDto);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

    @PostMapping("/registerKitchen")
    public ModelAndView registrarCocina(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse,Date birthday, @Valid RegisterDto registerDto, @Valid RegisterKitchenDto registerKitchenDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {

        if (br.hasErrors()) {

        } else {

            Ghosts u = new Ghosts();
            u.setAddress(registerDto.getAddress());
            u.setName(registerDto.getName());
            u.setEmail(registerDto.getEmail());
            u.setBirthday(registerDto.getBirthday());
            u.setPassword(codificator.encode(registerDto.getPassword()));
            u.setRole(Ghosts.GhostRole.Chef);
            userRepository.save(u);

            Chef c = new Chef();
            c.setWeb(registerKitchenDto.getWeb());
            c.setSchedules(registerKitchenDto.getSchedules());
            c.setUser(u);
            chefRepository.save(c);

        }
        ra.addFlashAttribute("message", "Usuario creado exitosamente");
        return new ModelAndView("redirect:/login");
    }

}
