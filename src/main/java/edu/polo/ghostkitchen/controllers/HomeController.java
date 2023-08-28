package edu.polo.ghostkitchen.controllers;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class HomeController {
    
   @Autowired
    DishRepository dishRepository;

    @Autowired
    DishService dishService;

    @RequestMapping("/")
    public ModelAndView home()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Inicio");
        maw.addObject("vista", "inicio/home");
/*
        long random = (long) ((Math.random() * (cursoRepositorio.count() - 1)) + 1);
        maw.addObject("curso", cursoServicio.getById(random));
*/
        //System.out.println( SecurityContextHolder.getContext().getAuthentication().getName() );
        return maw;  
    }

    @RequestMapping("/menu")
    public ModelAndView menu()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "menu");
        maw.addObject("vista", "inicio/menu");
        return maw;  
    }

    @RequestMapping("/perfil")
    public ModelAndView perfil()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "perfil");
        maw.addObject("vista", "inicio/perfil");
        return maw;  
    }

    @RequestMapping("/dishdetail")
    public ModelAndView dishdetail()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "dishdetail");
        maw.addObject("vista", "inicio/dishdetail");
        return maw;  
    }
}
