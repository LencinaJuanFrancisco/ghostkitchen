package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
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

    public List<Dish> getAllById(Long chefId) {
        // Obtén todos los platos de la base de datos
        List<Dish> allDishes = this.getAll();

        // Filtra los platos que corresponden al chefId dado
        List<Dish> dishesForChef = allDishes.stream()
                .filter(dish -> dish.getChef().getId().equals(chefId))
                .collect(Collectors.toList());

        return dishesForChef;
    }

    public List<Dish> getRandomDishes() {
    List<Dish> allDishes = (List<Dish>) dishRepository.findAll();

    List<Dish> randomDishes = new ArrayList<>(allDishes);

    Random random = new Random();
    for (int i = randomDishes.size() - 1; i > 0; i--) {
        int j = random.nextInt(i + 1); // Usamos nextInt para obtener un índice aleatorio
        // Intercambiamos los elementos en las posiciones i y j
        Dish temp = randomDishes.get(i);
        randomDishes.set(i, randomDishes.get(j));
        randomDishes.set(j, temp);
    }

    return randomDishes;
}
}
