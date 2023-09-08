package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.entidades.Chef;
import edu.polo.ghostkitchen.entidades.Client;
import edu.polo.ghostkitchen.entidades.Delivery;
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
import edu.polo.ghostkitchen.classes.CartAdm;
import edu.polo.ghostkitchen.entidades.Detail;
import edu.polo.ghostkitchen.entidades.Ghosts;
import edu.polo.ghostkitchen.entidades.Order;
import edu.polo.ghostkitchen.repositories.ClientRepository;
import edu.polo.ghostkitchen.repositories.DetailRepository;
import edu.polo.ghostkitchen.repositories.GhostsRepository;
import edu.polo.ghostkitchen.repositories.OrderRepository;
import edu.polo.ghostkitchen.services.CategoryService;
import edu.polo.ghostkitchen.services.DetailService;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GhostsRepository userRepository;

    @Autowired
    private CartAdm cartAdm;

    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    private DetailService detailService;

    @Autowired
    private DishService dishService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Declarar una lista para almacenar los objetos Dish
    @GetMapping("/pedido")
    public ModelAndView pedido() {
        ModelAndView modelAndView = new ModelAndView("fragments/base");
        modelAndView.addObject("titulo", "Orden");
        modelAndView.addObject("vista", "orden/order-details");
        modelAndView.addObject("cartAdm", cartAdm);
        modelAndView.addObject("allDetails", cartAdm.getDetailList());
        modelAndView.addObject("allcategory", categoryService.getAll());
        return modelAndView;
    }

    @PostMapping("/pedido/{dishId}")
    public String addDetailToOrder(@PathVariable Long dishId, @RequestParam("cantidad") Integer cantidad,
            @RequestParam("detalle") String detalle) {

        // Buscar el Dish en la base de datos por su ID
        Dish dish = dishService.getById(dishId);

        if (dish != null) {
            // Crear un nuevo objeto Dish con los campos deseados
            Dish newDish = new Dish();
            newDish.setId(dish.getId());
            newDish.setName(dish.getName());
            newDish.setPrice(dish.getPrice());
            newDish.setImage(dish.getImage());

            Detail detail = new Detail();
            detail.setCantidad(cantidad);
            detail.setDetalle(detalle);
            detail.setDish(dish);

            cartAdm.addDetail(detail);
        }

        return "redirect:/pedido"; // Redirigir a la página de detalles de la orden
    }

    @GetMapping("/vaciarLista")
    public String vaciarLista() {

        cartAdm.limpiar();
        return "redirect:/menu"; // Redirige a la página deseada después de vaciar la lista
    }

    @GetMapping("/pedido/eliminarDish/{id}")
    public String eliminarDish(@PathVariable Long id) {
        System.out.println("ID QUE DESEO ELIMINAR DEL LIST------------------" + id);
        // Supongamos que cada objeto Dish tiene un campo de identificación único (por ejemplo, "id")
        // Puedes buscar el objeto que deseas eliminar por su identificación
        Detail dishToRemove = null;
        for (Detail detail : cartAdm.getDetailList()) {
            if (detail.getDish().getId().equals(id)) {
                dishToRemove = detail;
                break;
            }
        }

        // Si se encuentra el objeto, eliminarlo de la lista
        if (dishToRemove != null) {
            cartAdm.removeOne(dishToRemove);
        }

        return "redirect:/pedido"; // Redirige a la página deseada después de eliminar el objeto
    }

    @GetMapping("/createOrden")
    public String createOrden() {
        Order order = new Order();
        orderRepository.save(order);

        //Delivery delivery = new Delivery();
        float prices = 0;

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Ghosts userFind = userRepository.findByEmail(user);
        Client clientFind = clientRepository.findClientByUserId(userFind.getId());
        order.setClient(clientFind);

        System.out.println(clientFind + "AAAAAAAAAAAAAAAAAA");

        for (Detail detail : cartAdm.getDetailList()) {
            Detail det = new Detail();
            det = detail;
            det.setOrder(order);
            prices = prices + ((det.getDish().getPrice()) * det.getCantidad());

            detailRepository.save(det);
            order.setChef(detail.getDish().getChef());

            //order.setPrice((detail.getCantidad() * detail.getDish().getPrice()));
        }

        order.setPrice(prices);
        order.setState(2);

        orderRepository.save(order);

        cartAdm.limpiar();
        return "redirect:/menu"; // Redirige a la página deseada después de vaciar la lista
    }
}
