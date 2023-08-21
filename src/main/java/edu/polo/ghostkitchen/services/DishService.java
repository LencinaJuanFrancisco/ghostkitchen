package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    public List<Dish> getAll() {
        List<Dish> lista = new ArrayList<Dish>();
        dishRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
    
    public Dish getById(Long id) {
        return dishRepository.findById(id).get();
    }
    
    public void save(Dish dish) {
        dishRepository.save(dish);
    }
    
    public void delete(Long id){
        dishRepository.deleteById(id);
    }
}
