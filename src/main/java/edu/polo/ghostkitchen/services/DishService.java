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

    public void save(Dish dish) {
        dishRepository.save(dish);
    }

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

    public List<Dish> getDishesByCategory(Category category) {
        return dishRepository.findByCategory(category);
    }

    public void update(Dish dishToUpdate) {
        // Verifica si el plato que se va a actualizar existe en la base de datos
        Dish existingDish = dishRepository.findById(dishToUpdate.getId()).orElse(null);

        if (existingDish != null) {
            // Actualiza los campos del plato existente con los valores del plato a actualizar
            existingDish.setName(dishToUpdate.getName());
            existingDish.setPrice(dishToUpdate.getPrice());
            existingDish.setDescription(dishToUpdate.getDescription());
            existingDish.setCategory(dishToUpdate.getCategory());
            existingDish.setDisponibility(dishToUpdate.isDisponibility());

            // Guarda el plato actualizado en la base de datos
            dishRepository.save(existingDish);
        } else {
            // Maneja el caso en el que el plato a actualizar no existe en la base de datos
            // Puedes lanzar una excepción o realizar otra acción según tus necesidades.
            throw new IllegalArgumentException("El plato a actualizar no existe en la base de datos.");
        }
    }
}
