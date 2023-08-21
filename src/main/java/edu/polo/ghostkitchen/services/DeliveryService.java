package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    public List<Delivery> getAll() {
        List<Delivery> lista = new ArrayList<Delivery>();
        deliveryRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
   
    public Delivery getById(Long id) {
        return deliveryRepository.findById(id).get();
    }
    
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }
    
    public void delete(Long id){
        deliveryRepository.deleteById(id);
    }
}
