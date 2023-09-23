package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends CrudRepository<Detail, Long> {

    //Plato mas vendido Dasboar Admin
    @Query("SELECT d.dish.id, COUNT(d.dish.id) FROM Detail d GROUP BY d.dish.id ORDER BY COUNT(d.dish.id) DESC")
    List<Object[]> platoMasVendido();

    // Plato más vendido por chef:
    @Query("SELECT d.dish.id AS dishId, dish.name AS dishName, SUM(d.cantidad) AS totalCantidad " +
    "FROM Detail d " +
    "JOIN Dish dish ON d.dish.id = dish.id " +
    "WHERE d.order.id IN (SELECT o.id FROM Order o WHERE o.chef.id = :chefId) " +
    "GROUP BY d.dish.id, dish.name " +
    "ORDER BY SUM(d.cantidad) DESC " +
    "LIMIT 1")
Object[] platoMasVendidoPorChef(@Param("chefId") Long chefId);
   

    // Plato menos vendido por chef:
    @Query("SELECT d.dish.id AS dishId, dish.name AS dishName, SUM(d.cantidad) AS totalCantidad " +
    "FROM Detail d " +
    "JOIN Dish dish ON d.dish.id = dish.id " +
    "WHERE d.order.id IN (SELECT o.id FROM Order o WHERE o.chef.id = :chefId) " +
    "GROUP BY d.dish.id, dish.name " +
    "ORDER BY SUM(d.cantidad)")
    Object[] platoMenosVendidoPorChef(@Param("chefId") Long chefId);



}
