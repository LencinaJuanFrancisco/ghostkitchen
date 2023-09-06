package edu.polo.ghostkitchen.controllers;

import org.springframework.web.multipart.MultipartFile;
import edu.polo.ghostkitchen.dto.CategoryDto;
import edu.polo.ghostkitchen.dto.DishDto;
import edu.polo.ghostkitchen.dto.RegisterKitchenDto;
import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import edu.polo.ghostkitchen.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private GhostsRepository userRepository;

    @Autowired
    private ChefRepository chefRepository;

    private CategoryDto categoryDto;

    private Dish dish;

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
        maw.addObject("dish", dish);
        maw.addObject("allcategory", categoryService.getAll());
        List<Category> allCategories = categoryService.getAll(); // Reemplaza "Category" con el nombre de tu entidad de categoría
        maw.addObject("allCategories", allCategories);
        return maw;
    }

    @PostMapping("/createDish")
    public ModelAndView creationDish(@RequestParam("image") MultipartFile image, @Valid DishDto dishDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {

        if (image.isEmpty()) {
            br.reject("image", "Por favor, cargue una imagen");
        }

        Dish d = new Dish();
        d.setName(dishDto.getName());
        d.setPrice(dishDto.getPrice());
        d.setRank(dishDto.getRank());
        d.setDisponibility(dishDto.isDisponibility());
        d.setCategory(dishDto.getCategory());
        d.setDescription(dishDto.getDescription());
        d = dishRepository.save(d);
        
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Ghosts userFind = userRepository.findByEmail(user);
        Chef chefFind = chefRepository.findChefsByUserId(userFind.getId());
        d.setChef(chefFind);

        String tipo = image.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String imagen = d.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/dishes", imagen).toAbsolutePath().toString();
        ModelAndView mav = this.createDish(dishDto);

        try {
            image.transferTo(new File(path));
        } catch (Exception e) {
            mav.addObject("error", "No se pudo guardar la imagen");
            return mav;
        }
        d.setImage(imagen);
        dishRepository.save(d);

        ra.addFlashAttribute("message", "Plato creado exitosamente");
        return new ModelAndView("redirect:/menu");
    }

}
