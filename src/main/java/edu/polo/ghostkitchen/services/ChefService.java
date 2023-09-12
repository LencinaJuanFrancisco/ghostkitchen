package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    
    public List<Chef> getRandomChefs() {
    List<Chef> allChefs = (List<Chef>) chefRepository.findAll();

    List<Chef> randomChefs = new ArrayList<>(allChefs);

    Random random = new Random();
    for (int i = randomChefs.size() - 1; i > 0; i--) {
        int j = random.nextInt(i + 1); // Usamos nextInt para obtener un índice aleatorio
        // Intercambiamos los elementos en las posiciones i y j
        Chef temp = randomChefs.get(i);
        randomChefs.set(i, randomChefs.get(j));
        randomChefs.set(j, temp);
    }

    return randomChefs;
}

}
