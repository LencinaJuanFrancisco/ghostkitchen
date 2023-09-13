package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    Dish findByName(String name);

    Dish findByDescription(String description);

    Dish findByDisponibility(boolean disponibility);

    Dish findByRank(float rank);

    Dish findByPrice(Long price);

    @Query("SELECT COUNT(*) FROM Dish")
int countDishes();
}
