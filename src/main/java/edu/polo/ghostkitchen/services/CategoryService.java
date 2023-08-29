package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

   public List<Category> getAll() {
    return (List<Category>) categoryRepository.findAll();
}
    
    public Category getById(Long id) {
        return categoryRepository.findById(id).get();
    }
    
    public void save(Category category) {
        categoryRepository.save(category);
    }
    
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
