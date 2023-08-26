package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.Ghosts;
import edu.polo.ghostkitchen.repositories.GhostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GhostsService {

    private final GhostsRepository userRepository;

    @Autowired
    public GhostsService(GhostsRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Ghosts> getAllUsers() {
        return userRepository.findAll();
    }

    public Ghosts getUserById(Long id) {
        Optional<Ghosts> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public Ghosts createUser(Ghosts user) {
        return userRepository.save(user);
    }

    public Ghosts updateUser(Long id, Ghosts user) {
        user.setId(id); // Set the ID of the user to update
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

