package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.entidades.Category;
import edu.polo.ghostkitchen.repositories.CategoryRepository;
import edu.polo.ghostkitchen.dto.CategoryDto;
import edu.polo.ghostkitchen.validations.CategoryValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
public class AdminController implements WebMvcConfigurer {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryValidator categoryValidator;

    @GetMapping("/createcategory")
    public ModelAndView createcategory(CategoryDto categoryDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear categoria");
        maw.addObject("vista", "category/createcategory");
        maw.addObject("categoryDto", categoryDto);
        return maw;
    }

    /*  @PostMapping("/createcategory")
    public ModelAndView registrar(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse, @Valid CategoryDto categoryDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {
        // ... Validación de reCAPTCHA y otros controles de errores ...

        // Registrar al usuario y guardar en la base de datos
        Category u = new Category();
        u.setCategory(categoryDto.getCategory());
        u.setDescription(categoryDto.getDescription());
        categoryRepository.save(u);

        // Redirigir al usuario a la página de inicio de sesión
        return new ModelAndView("redirect:/home");
    }*/
    @PostMapping("/createcategory")
    public ModelAndView registrar(CategoryDto categoryDto, BindingResult br) {
        // ... Validación de otros controles de errores ...
        categoryValidator.validate(categoryDto, br);
        if (br.hasErrors()) {
            // Si hay errores de validación, redirigir de vuelta al formulario de creación con los mensajes de error
            ModelAndView maw = new ModelAndView();
            maw.setViewName("fragments/base");
            maw.addObject("titulo", "Crear categoria");
            maw.addObject("vista", "category/createcategory");
            maw.addObject("categoryDto", categoryDto);
            maw.addObject("org.springframework.validation.BindingResult.categoryDto", br);
            return maw;
        }

        // Registrar la categoría y guardar en la base de datos
        Category u = new Category();
        u.setCategory(categoryDto.getCategory());
        u.setDescription(categoryDto.getDescription());
        categoryRepository.save(u);

        // Redirigir al usuario a alguna página después del registro exitoso
        return new ModelAndView("redirect:/home");
    }
    /*
@GetMapping
public ModelAndView index() {
   ModelAndView maw = new ModelAndView();
    maw.setViewName("fragments/base");
     maw.addObject("titulo","Categorias");
    maw.addObject("vista","category/index");
  maw.addObject("allcategory",categoryService.getAll());
    // Registrar la categoría y guardar en la base de datos(
    System.out.println("AAAAAAAAAAAAAAAAAAAAAA"+categoryService.getAll());
          
  return maw;
}*/
}
