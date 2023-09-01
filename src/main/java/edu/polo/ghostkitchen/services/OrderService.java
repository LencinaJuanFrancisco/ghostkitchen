package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }
<<<<<<< HEAD

    public Order getById(Long id) {
        return orderRepository.findById(id).get();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void delete(Long id) {
=======
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        // Puedes realizar validaciones o lógica de negocio aquí antes de guardar la orden
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
>>>>>>> devs
        orderRepository.deleteById(id);
    }
}
