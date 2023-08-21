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

    public List<Order> getAll() {
        List<Order> lista = new ArrayList<Order>();
        orderRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
    
    public Order getById(Long id) {
        return orderRepository.findById(id).get();
    }
    
    public void save(Order order) {
        orderRepository.save(order);
    }
    
    public void delete(Long id){
        orderRepository.deleteById(id);
    }
}
