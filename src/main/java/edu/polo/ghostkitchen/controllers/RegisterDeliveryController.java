package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.dto.RegisterDeliveryDto;
import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.polo.ghostkitchen.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegisterDeliveryController {

    @Autowired
    private BCryptPasswordEncoder codificator;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GhostsRepository userRepository;

    @GetMapping("/registerDelivery")
    public ModelAndView registerDelivery(RegisterDeliveryDto registerDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Registrarse");
        maw.addObject("vista", "auth/registerDelivery");
        maw.addObject("registerDeliveryDto", registerDto);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

    @PostMapping("/registerDelivery")
    public ModelAndView registrarDelivery(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, @Valid RegisterDto registerDto, @Valid RegisterDeliveryDto registerDeliveryDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {

        if (br.hasErrors()) {

        } else {

            Ghosts u = new Ghosts();
            u.setAddress(registerDto.getAddress());
            u.setName(registerDto.getName());
            u.setEmail(registerDto.getEmail());
            u.setBirthday(registerDto.getBirthday());
            u.setPassword(codificator.encode(registerDto.getPassword()));
            u.setRole(Ghosts.GhostRole.Delivery);
            userRepository.save(u);

            Delivery d = new Delivery();
            d.setLicencePlate(registerDeliveryDto.getLicencePlate());
            d.setUser(u);
            deliveryRepository.save(d);

        }
        ra.addFlashAttribute("message", "Usuario creado exitosamente");
        return new ModelAndView("redirect:/login");
    }

}
