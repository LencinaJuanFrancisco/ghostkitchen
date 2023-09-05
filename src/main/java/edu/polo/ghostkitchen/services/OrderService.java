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
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
