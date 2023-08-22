package edu.polo.ghostkitchen.controlators;


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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthControlator {
    
    @Autowired
    private BCryptPasswordEncoder codificator;

    @Autowired
    private GhostRepository userRepository;

    @Autowired
    private RecaptchaService recaptchaService;

    @GetMapping("/login")
    public ModelAndView showLoginForm(Model model, 
        @RequestParam(name = "error", required = false) String error,
        @RequestParam(name="logout", required = false) String logout) {
            
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Iniciar sesión");
        maw.addObject("vista", "auth/login");
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return maw;
    }

    @GetMapping({"/loginSuccess"})
    public RedirectView loginCheck(){
        return new RedirectView("/");
    }
    
    @GetMapping("/register")
	public ModelAndView register(RegisterDto registerDto)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Registrarse");
        maw.addObject("vista", "auth/register");
        maw.addObject("registerDto", registerDto);
        return maw;
	}

	@PostMapping("/register")
	public ModelAndView registrar(@RequestParam(name="g-recaptcha-response") String recaptchaResponse, @Valid RegisterDto registerDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request)
    {
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = recaptchaService.verifyRecaptcha(ip, recaptchaResponse);

        if (captchaVerifyMessage != "") {
            br.rejectValue("recaptcha", "recaptcha", captchaVerifyMessage);
        }

        if ( br.hasErrors() ) {
			return this.register(registerDto);
		}

        Ghost u = new Ghost();
        u.setEmail(registerDto.getEmail());
        u.setPassword(codificator.encode(registerDto.getPassword()));
        u.setRole(Ghost.UserRole.Client);
		

        try {
            request.login(registerDto.getEmail(), registerDto.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        HomeControlator hc = new HomeControlator();
        return hc.home();
	}


}
