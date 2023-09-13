package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT COUNT(o) FROM Order o")
    int getCantidadDeOrdenes();

    @Query("SELECT SUM(o.price) FROM Order o")
    double getTotalDeVentas();

    @Query("SELECT SUM(o.price) * 0.10 FROM Order o")
    double getGanancia();

    @Query("SELECT o.chef.id, COUNT(o) AS ventas_totales FROM Order o GROUP BY o.chef.id ORDER BY ventas_totales DESC")
    List<Object[]> chefMasOrdenes();

    @Query("SELECT o.client.id FROM Order o GROUP BY o.client.id ORDER BY COUNT(o.client.id) DESC")
    List<Object[]> clienteMasCompro();
}
