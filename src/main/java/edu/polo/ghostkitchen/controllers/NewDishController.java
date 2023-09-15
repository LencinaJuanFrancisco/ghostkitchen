package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.classes.CartAdm;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private CartAdm cartAdm;

    @GetMapping("/createDish")
    public ModelAndView createDish(DishDto dishDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear plato");
        maw.addObject("vista", "dish/createDish");
        maw.addObject("dishDto", dishDto);
        maw.addObject("dish", dish);
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("allcategory", categoryService.getAll());
        List<Category> allCategories = categoryService.getAll(); // Reemplaza "Category" con el nombre de tu entidad de categoría
        maw.addObject("allCategories", allCategories);
        return maw;
    }

    @PostMapping("/createDish")
    public ModelAndView creationDish(@RequestParam("image") MultipartFile image, @Valid DishDto dishDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) throws InterruptedException {

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

        Thread.sleep(1500);

        ra.addFlashAttribute("message", "Plato creado exitosamente");
        return new ModelAndView("redirect:/menu");
    }

//     @GetMapping("/editDish/{id}")
//    public String showEditDishForm(@PathVariable Long id, Model model) {
//        Dish dish = dishService.getById(id);
//        model.addAttribute("dish", dish);
//        // También necesitas agregar otras variables necesarias como las categorías.
//        return "editDishForm";
//    }
//
//    @PostMapping("/editDish/{id}")
//    public String editDish(@PathVariable Long id, @ModelAttribute("dish") Dish updatedDish, Model model) {
//        Dish dish = dishService.getById(id);
//        // Actualiza los campos del plato existente con los valores de updatedDish.
//        dish.setName(updatedDish.getName());
//        dish.setPrice(updatedDish.getPrice());
//        // Actualiza otros campos según sea necesario.
//        dishService.save(dish); // Guarda los cambios en la base de datos.
//
//        // Redirige a la página de detalles del plato actualizado o a donde desees.
//        return "redirect:/dishDetails/" + id;
//    }
    @RequestMapping("/editDish/{dishId}")
    public ModelAndView perfil(@PathVariable Long dishId) throws InterruptedException {

        Dish dish = dishService.getById(dishId);

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar plato");
        maw.addObject("vista", "dish/editDish");
        maw.addObject("dish", dish);
        List<Category> allCategories = categoryService.getAll();
        maw.addObject("allCategories", allCategories);
        Thread.sleep(1000);
        return maw;
    }

    @PostMapping("/editDish/{dishId}")
    public String guardarCambios(@PathVariable Long dishId, @ModelAttribute("dish") Dish dish) {
        // Aquí debes actualizar la información del plato en la base de datos
        // Utiliza el objeto 'dish' para acceder a los datos del formulario
        Dish dishToUpdate = dishService.getById(dishId); // Obtén el plato actual de la base de datos
        dishToUpdate.setName(dish.getName()); // Actualiza los campos necesarios
        dishToUpdate.setPrice(dish.getPrice());
        dishToUpdate.setDescription(dish.getDescription());
        dishToUpdate.setCategory(dish.getCategory());
        dishToUpdate.setDisponibility(dish.isDisponibility());
        dishService.update(dishToUpdate); // Actualiza el plato en la base de datos

        // Redirecciona a la página principal o a donde desees después de guardar los cambios
        return "redirect:/";
    }

    @GetMapping("/deleteDish/{dishId}")
    public String borrarPlato(@PathVariable Long dishId) {
        // Aquí debes actualizar la información del plato en la base de datos
        // Utiliza el objeto 'dish' para acceder a los datos del formulario
//         Dish dishToUpdate = dishService.getById(dishId); // Obtén el plato actual de la base de datos

        dishService.delete(dishId);

//        dishToUpdate.dele; // Actualiza el plato en la base de datos
        // Redirecciona a la página principal o a donde desees después de guardar los cambios
        return "redirect:/perfilChef";
    }
}
