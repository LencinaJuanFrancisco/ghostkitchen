package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.classes.CartAdm;
import edu.polo.ghostkitchen.classes.DashboarInfo;
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
    private DashboarInfo dashboarInfo;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private CartAdm cartAdm;

    @GetMapping("/createcategory")
    public ModelAndView createcategory(CategoryDto categoryDto) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear categoria");
        maw.addObject("cartAdm", cartAdm);
        maw.addObject("vista", "category/createcategory");
        maw.addObject("categoryDto", categoryDto);
        return maw;
    }

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
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/dashboard/home")
    public ModelAndView dashboard() throws InterruptedException {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Dashboard");
        maw.addObject("vista", "fragments/homeDash");
        maw.addObject("ordenes", dashboarInfo.getCantidadDeOrdenes());
        maw.addObject("totalVentas", dashboarInfo.getTotalDeVentas());
        maw.addObject("ganancias", dashboarInfo.getGanancia());
        maw.addObject("clientesTotales", dashboarInfo.getCantidadDeUsuarios());
        maw.addObject("compradores", dashboarInfo.getCantidadDeCompradores());
        maw.addObject("cocinas", dashboarInfo.getCantidadDeCocineros());
        maw.addObject("deliveries", dashboarInfo.getCantidadDeDelivery());
        maw.addObject("cantidadDePlatos", dashboarInfo.getCantidadDePlatos());
        maw.addObject("platoMasVendido", dashboarInfo.getPlatoMasVendido());
        maw.addObject("clienteMasCompra", dashboarInfo.getClienteQueMasCompro());
        maw.addObject("chefMasVendido", dashboarInfo.getCocinaMasVendida());

//            System.out.println("ttttttttttttttttttttttttttttttttttttttt");
//            Object[] arreglo = dashboarInfo.getClienteQueMasCompro();
// for (int i = 0; i < arreglo.length; i++) {
//     Object elemento = arreglo[i];
//    
//     System.out.println(elemento.toString());
// }
// System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
      
Thread.sleep(800);

        return maw;

    }

    @GetMapping("/dashboard/usuarios")
    public ModelAndView usuarios() {

        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/homeDash");
        maw.addObject("titulo", "Categorias");
        maw.addObject("dash", "fragments/usuarioDash"); // Ubicación completa del fragmento
        return maw;
    }
}
