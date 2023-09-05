package edu.polo.ghostkitchen.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;


import edu.polo.ghostkitchen.services.DishService;
import edu.polo.ghostkitchen.services.OrderService;
import edu.polo.ghostkitchen.entidades.Detail;
import edu.polo.ghostkitchen.entidades.Dish;
import	edu.polo.ghostkitchen.entidades.Order;
import edu.polo.ghostkitchen.entidades.Detail;
import edu.polo.ghostkitchen.entidades.Order;
import edu.polo.ghostkitchen.services.CategoryService;



@Controller
public class OrderController {

   private final OrderService orderService;

   @Autowired
    private CategoryService categoryService;
   
    @Autowired
    private DishService dishService; 

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

      // Declarar una lista para almacenar los objetos Dish
    private List<Dish> dishList = new ArrayList<>();



   @GetMapping("/pedido")
public ModelAndView pedido() {
    ModelAndView modelAndView = new ModelAndView("fragments/base");
    modelAndView.addObject("titulo", "Orden");
    modelAndView.addObject("vista", "orden/order-details");
    modelAndView.addObject("allOrders", dishList);
    modelAndView.addObject("carritoVacio", dishList.isEmpty());
     modelAndView.addObject("allcategory", categoryService.getAll());
    return modelAndView;
}


   
@PostMapping("/pedido/{dishId}")
    public String addDetailToOrder(@PathVariable Long dishId, @RequestParam("cantidad") Integer cantidad) {
        
        // Tu código aquí para procesar los datos
        System.out.println("id plato: " + dishId);
        System.out.println("cantidad: " + cantidad);
        
         // Buscar el Dish en la base de datos por su ID
        Dish dish = dishService.getById(dishId);


        if (dish != null) {
            // Crear un nuevo objeto Dish con los campos deseados
            Dish newDish = new Dish();
            newDish.setId(dish.getId());
            newDish.setName(dish.getName());
            newDish.setPrice(dish.getPrice());
            // newDish.setCantidad(cantidad);

            // Agregar el objeto Dish a la lista
            dishList.add(newDish);
        }
        
       
        return "redirect:/pedido"; // Redirigir a la página de detalles de la orden
    }
    
    

    @GetMapping("/vaciarLista")
    public String vaciarLista() {
        dishList.clear();
        return "redirect:/menu"; // Redirige a la página deseada después de vaciar la lista
    }


   @GetMapping("/pedido/eliminarDish/{id}")
    public String eliminarDish(@PathVariable Long id) {
        System.out.println("ID QUE DESEO ELIMINAR DEL LIST------------------" + id);
        // Supongamos que cada objeto Dish tiene un campo de identificación único (por ejemplo, "id")
        // Puedes buscar el objeto que deseas eliminar por su identificación
        Dish dishToRemove = null;
        for (Dish dish : dishList) {
            if (dish.getId().equals(id)) {
                dishToRemove = dish;
                break;
            }
        }

        // Si se encuentra el objeto, eliminarlo de la lista
        if (dishToRemove != null) {
            dishList.remove(dishToRemove);
        }

        return "redirect:/pedido"; // Redirige a la página deseada después de eliminar el objeto
    }
}
