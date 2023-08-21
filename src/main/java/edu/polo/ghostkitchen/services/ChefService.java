package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ChefService {

    @Autowired
    ChefRepository chefRepository;

    public List<Chef> getAll() {
        List<Chef> lista = new ArrayList<Chef>();
        chefRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
    
    public Chef getById(Long id) {
        return chefRepository.findById(id).get();
    }
    
    public void save(Chef chef) {
        chefRepository.save(chef);
    }
    
    public void delete(Long id){
        chefRepository.deleteById(id);
    }
}
