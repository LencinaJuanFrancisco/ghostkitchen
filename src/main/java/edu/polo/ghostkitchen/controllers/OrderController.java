package edu.polo.ghostkitchen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import edu.polo.ghostkitchen.services.OrderService;
import edu.polo.ghostkitchen.entidades.Detail;
import edu.polo.ghostkitchen.entidades.Order;
import edu.polo.ghostkitchen.services.CategoryService;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/allOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/pedido")
    public ModelAndView pedido() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Orden");
        maw.addObject("vista", "orden/order-details");
        maw.addObject("allcategory", categoryService.getAll());
        return maw;
    }

    @PostMapping("/{orderId}/add-detail")
    public String addDetailToOrder(@PathVariable Long orderId, @ModelAttribute Detail detail) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            // Agregar el detalle a la orden
            order.addDetail(detail);
            orderService.createOrder(order);
        }
        return "redirect:/pedido/" + orderId; // Redirigir a la página de detalles de la orden
    }

    @PostMapping("/{orderId}/remove-detail")
    public String removeDetailFromOrder(@PathVariable Long orderId, @RequestParam Long detailId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            // Remover el detalle de la orden
            order.removeDetailById(detailId);
            orderService.createOrder(order);
        }
        return "redirect:/orders/" + orderId; // Redirigir a la página de detalles de la orden
    }

}
