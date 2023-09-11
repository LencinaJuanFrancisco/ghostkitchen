package edu.polo.ghostkitchen.controllers;

import edu.polo.ghostkitchen.entidades.Chef;
import edu.polo.ghostkitchen.entidades.Client;
import edu.polo.ghostkitchen.entidades.Delivery;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.model.Model;
import edu.polo.ghostkitchen.services.DishService;
import edu.polo.ghostkitchen.services.OrderService;
import jakarta.servlet.http.HttpSession;
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

    Long chefId = 0L;
    List<Detail> cart ;
    
    @GetMapping("/remito")
    public ModelAndView remito(HttpSession session) {

        // Recupera los objetos de la sesión
        Order order = (Order) session.getAttribute("order");
      
        // String fechaHoy = (String) session.getAttribute("fechaHoy");
        

        ModelAndView modelAndView = new ModelAndView("fragments/base");
        modelAndView.addObject("titulo", "Remito");
        modelAndView.addObject("vista", "orden/remito");
         modelAndView.addObject("cart", cart);
        // Puedes agregar los objetos al modelo para usarlos en tu vista
        modelAndView.addObject("order", order);
       modelAndView.addObject("ocultarEncabezado", true);
       

        // modelAndView.addObject("fecha", fechaHoy);

        return modelAndView;
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
        modelAndView.addObject("chefId", chefId);
       
        return modelAndView;
    }
 @GetMapping("/finalizarRemito")
    public String finalizarRemito(HttpSession session) {

        cart.clear();
        session.removeAttribute("order");
        

        return "redirect:/menu";
    }


    @PostMapping("/pedido/{dishId}")
    public String addDetailToOrder(@PathVariable Long dishId, @RequestParam("cantidad") Integer cantidad,
            @RequestParam("detalle") String detalle) {

        // Buscar el Dish en la base de datos por su ID
        Dish dish = dishService.getById(dishId);
        chefId = dish.getChef().getId();
        System.out.println(chefId + "ESTE ES OTRO NUMERO");
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
        chefId = 0L;
        return "redirect:/menu"; // Redirige a la página deseada después de vaciar la lista
    }

    @GetMapping("/pedido/eliminarDish/{id}")
    public String eliminarDish(@PathVariable Long id) {
        System.out.println("ID QUE DESEO ELIMINAR DEL LIST------------------" + id);

        // buscar el objeto que deseas eliminar por su identificación
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
        if (cartAdm.getSizeList() == 0) {
            chefId = 0L;
        }

        return "redirect:/pedido"; // Redirige a la página deseada después de eliminar el objeto
    }

    @GetMapping("/createOrden")
    public String createOrden(HttpSession session) {
        Order order = new Order();
        orderRepository.save(order);

        // Delivery delivery = new Delivery();
        float prices = 0;

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Ghosts userFind = userRepository.findByEmail(user);
        Client clientFind = clientRepository.findClientByUserId(userFind.getId());
        order.setClient(clientFind);

        for (Detail detail : cartAdm.getDetailList()) {
            Detail det = new Detail();
            det = detail;
            det.setOrder(order);
            prices = prices + ((det.getDish().getPrice()) * det.getCantidad());

            detailRepository.save(det);
            order.setChef(detail.getDish().getChef());

            // order.setPrice((detail.getCantidad() * detail.getDish().getPrice()));
        }

        order.setPrice(prices);
        order.setState(2);

        orderRepository.save(order);

        // Guarda los objetos en la sesión
        session.setAttribute("order", order);
       
        //guardo todo lo que esta en el carrito par rellenar el remito
        cart = cartAdm.getDetailList();
        // // Obtiene la fecha actual
        // Date fechaActual = new Date(0,0,0);
        // // Define el formato deseado para la fecha
        // SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        // // Formatea la fecha actual en el formato "dd/MM/yyyy"
        // String fechaFormateada = formatoFecha.format(fechaActual);
        // // Almacena la fecha formateada en la sesión
        // session.setAttribute("fechaHoy", fechaFormateada);
        System.out.println("------------vvvvv------------");
        System.out.println(cart);
        System.out.println("----------xxxxx---------------");

        //cartAdm.limpiar();
        return "redirect:/remito"; // Redirige a la página deseada después de vaciar la lista
    }

}
