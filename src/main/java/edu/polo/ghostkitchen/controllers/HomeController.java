package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.classes.CartAdm;
import edu.polo.ghostkitchen.entidades.Category;
import edu.polo.ghostkitchen.entidades.Chef;
import edu.polo.ghostkitchen.entidades.Dish;
import edu.polo.ghostkitchen.entidades.Ghosts;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    ChefService chefService;

    @Autowired
    private CartAdm cartAdm;

    @RequestMapping("/")
    public ModelAndView home(HttpSession session) {

         String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
        System.out.println("OOOOOOOOOOOOOOOO"+ (username) + "OOOOOOOOOOOOOOOOOOOOOOOOOO");
         Ghosts userFind = userRepository.findByEmail(username);
         session.setAttribute("userLogged", userFind);
         
        List<Chef> randomChefs = chefService.getRandomChefs();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Inicio");
        maw.addObject("vista", "inicio/home");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("allcategory", categoryService.getAll());
        maw.addObject("randomChefs", randomChefs);
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

        List<Dish> randomDishes = dishService.getRandomDishes();

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Menú");
        maw.addObject("vista", "inicio/menu");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("allcategory", categoryService.getAll());
        maw.addObject("allDishes", dishService.getAll());
        maw.addObject("pageTitle", "Menú - Todos");
        maw.addObject("randomDishes", randomDishes);
        return maw;
    }

    @RequestMapping("/menu/{categoryName}")
    public ModelAndView menuCategory(@PathVariable String categoryName) {

        Category category = categoryService.getByCategoryCategory(categoryName);
        List<Dish> dishes = dishService.getDishesByCategory(category);

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Menú");
        maw.addObject("vista", "category/menuCategory");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("category", category);
        maw.addObject("dishes", dishes);
        maw.addObject("pageTitle", "Menú - Categoría: " + categoryName);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

     @RequestMapping("/perfil")
    public ModelAndView perfilUser(HttpSession session) {

       
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Perfil");
        maw.addObject("vista", "inicio/perfilUser");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("allcategory", categoryService.getAll());
        maw.addObject("userLogged", session.getAttribute("userLogged"));
        return maw;
    }
    
   @RequestMapping("/perfilChef")
    public ModelAndView perfil(HttpSession session) {

      String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
       
         Ghosts userFind = userRepository.findByEmail(username);
         Chef chefFind = chefService.findChefsByUserId(userFind.getId());
         session.setAttribute("userLogged", chefFind);
        
       
        
        List<Dish> dish = dishService.getAllById(chefFind.getId());

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Perfil");
        maw.addObject("vista", "inicio/perfilChef");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("chef", chefFind);
        maw.addObject("dish", dish);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }
    
    @RequestMapping("/perfil/{chefId}")
    public ModelAndView perfil(@PathVariable Long chefId) {

        Chef chef = chefService.getById(chefId);
        List<Dish> dish = dishService.getAllById(chefId);

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Perfil");
        maw.addObject("vista", "inicio/perfil");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("chef", chef);
        maw.addObject("dish", dish);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }
    
    
    
    
    

    @RequestMapping("/dishdetail")
    public ModelAndView dishdetail() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "dishdetail");
        maw.addObject("vista", "inicio/dishdetail");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }
}
