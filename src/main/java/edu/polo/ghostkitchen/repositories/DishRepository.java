package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    List<Dish> findByCategory(Category category);

    //Plato con mayor ranking por chef:
    @Query("SELECT d.id AS dish_id, d.name AS nombre_plato, AVG(d.rank) AS avg_rank " +
    "FROM Dish d " +
    "WHERE d.chef.id = :chefId " +
    "GROUP BY d.id, d.name " +
    "ORDER BY avg_rank DESC")
     Object[] platoMayorRankinPorChef(@Param("chefId") Long chefId);

    //Plato con menor ranking por chef:
      @Query("SELECT d.id AS dish_id, d.name AS nombre_plato, AVG(d.rank) AS avg_rank " +
    "FROM Dish d " +
    "WHERE d.chef.id = :chefId " +
    "GROUP BY d.id, d.name " +
    "ORDER BY avg_rank")
    Object[] platoMenorRankinPorChef(@Param("chefId") Long chefId);

    //Promedio de ranking por chef:
    @Query("SELECT AVG(d.dish.rank) FROM Detail d WHERE d.order.chef.id = :chefId")
    Double promedioDeRankingDePlatosPorChef(@Param("chefId") Long chefId);



}
