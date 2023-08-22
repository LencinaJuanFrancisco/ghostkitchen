package edu.polo.ghostkitchen.controlators;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class HomeControlator {
    
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

    @RequestMapping("/ejemplo")
    public ModelAndView ejemplo()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Ejemplo");
        maw.addObject("vista", "inicio/ejemplo");
        return maw;  
    }
}
