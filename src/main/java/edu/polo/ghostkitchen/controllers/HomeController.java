package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    GhostsRepository userRepository;

    @Autowired
    DishService dishService;

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Inicio");
        maw.addObject("vista", "inicio/home");
        maw.addObject("allcategory", categoryService.getAll());
        /*
        long random = (long) ((Math.random() * (cursoRepositorio.count() - 1)) + 1);
        maw.addObject("curso", cursoServicio.getById(random));
         */

//        System.out.println( "QUE ES ESTO?" + (SecurityContextHolder.getContext().getAuthentication().getName()) );
//        String user = SecurityContextHolder.getContext().getAuthentication().getName();
//        Ghosts userFind = userRepository.findByEmail(user);
//      
//        System.out.println(userFind);
        return maw;
    }

    @RequestMapping("/menu")
    public ModelAndView menu() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Menú");
        maw.addObject("vista", "inicio/menu");
        maw.addObject("allcategory", categoryService.getAll());
        maw.addObject("allDishes", dishService.getAll());
        return maw;
    }

    @RequestMapping("/perfil")
    public ModelAndView perfil() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Perfil");
        maw.addObject("vista", "inicio/perfil");
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

    @RequestMapping("/dishdetail")
    public ModelAndView dishdetail() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "dishdetail");
        maw.addObject("vista", "inicio/dishdetail");
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }
}
