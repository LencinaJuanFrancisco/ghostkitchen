package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    public Chef getById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }

    public Chef getByWeb(String web) {
        return chefRepository.findByWeb(web);
    }
    
    public Chef findChefsByUserId(Long userId) {
        return chefRepository.findChefsByUserId(userId);
    }

    public Object getAll() {
        return (List<Chef>) chefRepository.findAll();   }

}
