package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public Dish getById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish getByName(String name) {
        return dishRepository.findByName(name);
    }
    
    public Dish getByRank(float rank) {
        return dishRepository.findByRank(rank);
    }
    
    public Dish getByDisponibility(boolean disponibility) {
        return dishRepository.findByDisponibility(disponibility);
    }
    
    public Dish getByPrice(Long price) {
        return dishRepository.findByPrice(price);
    }

    public List<Dish> getAll() {
        return (List<Dish>) dishRepository.findAll();
    }
    
   
}

