package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.dto.CategoryDto;
import edu.polo.ghostkitchen.dto.DishDto;
import edu.polo.ghostkitchen.dto.RegisterKitchenDto;
import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NewDishController {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryRepository categoryRepository;

    
    private CategoryDto categoryDto;

 
    private DishDto dishDto;
    
  
    private RegisterKitchenDto registerKitchenDto;
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/createDish")
    public ModelAndView createDish(DishDto dishDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear plato");
        maw.addObject("vista", "dish/createDish");
        maw.addObject("dishDto", dishDto);
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

    @PostMapping("/createDish")
    public ModelAndView creationDish(@Valid DishDto dishDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {

        if (br.hasErrors()) {

        } else {

//            Chef ch = new Chef();
//            ch.setWeb(registerKitchenDto.getWeb());
//            ch.setSchedules(registerKitchenDto.getSchedules());
//
        //    Category c = new Category();
        //    c.setCategory(categoryDto.getCategory());
        //    c.setDescription(categoryDto.getDescription());
        //    categoryRepository.save(c);

            Dish d = new Dish();
            d.setName(dishDto.getName());
            d.setPrice(dishDto.getPrice());
            d.setRank(dishDto.getRank());
            d.setDisponibility(dishDto.isDisponibility());
            
         //   d.setCategory(c);
         //   d.setChef(ch);
            dishRepository.save(d);

        }
        ra.addFlashAttribute("message", "Plato creado exitosamente");
        return new ModelAndView("redirect:/login");
    }

}


