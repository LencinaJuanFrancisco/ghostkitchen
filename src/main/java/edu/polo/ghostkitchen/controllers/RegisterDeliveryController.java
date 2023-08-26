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
public class RegisterDeliveryController {
    
    @Autowired
    private BCryptPasswordEncoder codificator;

    @Autowired
    private GhostsRepository userRepository;

    @Autowired
    private RecaptchaService recaptchaService;
    
     @Autowired
    private GhostService ghostService;

 
   @GetMapping("/registerDelivery")
	public ModelAndView registerDelivery(RegisterKitchenDto registerDto)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Registrarse");
        maw.addObject("vista", "auth/registerDelivery");
        maw.addObject("registerDeliveryDto", registerDto);
        return maw;
	}
    

	@PostMapping("/registerDelivery")
public ModelAndView registrar(@RequestParam(name="g-recaptcha-response") String recaptchaResponse, @Valid RegisterDto registerDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {
    // ... Validación de reCAPTCHA y otros controles de errores ...

    // Registrar al usuario y guardar en la base de datos
    Ghosts u = new Ghosts();
    u.setAddress(registerDto.getAddress());
    u.setName(registerDto.getName());
    u.setEmail(registerDto.getEmail());
    u.setPassword(codificator.encode(registerDto.getPassword()));
    u.setRole(Ghosts.GhostRole.Delivery);
    ghostService.register(u);

    // Redirigir al usuario a la página de inicio de sesión
    return new ModelAndView("redirect:/login");
}


}
