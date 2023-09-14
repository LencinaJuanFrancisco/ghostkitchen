package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    Dish findByName(String name);

    Dish findByDescription(String description);

    Dish findByDisponibility(boolean disponibility);

    Dish findByRank(float rank);

    Dish findByPrice(Long price);

    List<Dish> findByCategory(Category category);
    
}
