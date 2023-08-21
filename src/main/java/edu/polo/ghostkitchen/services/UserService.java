package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        List<User> lista = new ArrayList<User>();
        userRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
    
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
    
    public void save(User user) {
        userRepository.save(user);
    }
    
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
