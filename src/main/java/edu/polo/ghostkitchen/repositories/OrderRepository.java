package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT o.client.id, COUNT(o.client.id) AS totalCompras " +
            "FROM Order o " +
            "GROUP BY o.client.id " +
            "ORDER BY totalCompras DESC")
    List<Object[]> clienteMasCompro();

    // Cantidad de órdenes vendidas por chef:
    @Query("SELECT COUNT(o) FROM Order o WHERE o.chef.id = :chefId")
    Long cantidadDeOrdenesPorChef(@Param("chefId") Long chefId);

    // Cantidad de platos vendidos por chef:
    @Query("SELECT SUM(d.cantidad) FROM Order o JOIN o.details d WHERE o.chef.id = :chefId")
    Integer cantidadDePlatosVendidosPorChef(@Param("chefId") Long chefId);

    // Ganancia por chef
    @Query("SELECT SUM(o.price) AS total_ventas FROM Order o WHERE o.chef.id = :chefId")
    Double totalVentaPorChef(@Param("chefId") Long chefId);

    //porcentaje de platos vendidos por chef
    @Query("SELECT " +
            "   D.name AS nombre_plato, " +
            "   COUNT(*) AS total_ordenes_por_plato, " +
            "   (COUNT(*) * 100) / ( " +
            "       SELECT COUNT(*) " +
            "       FROM Detail DET " +
            "       WHERE DET.order.id IN ( " +
            "           SELECT og.id " +
            "           FROM Order og " +
            "           WHERE og.chef.id = :chefId " +
            "       ) " +
            "   ) AS porcentaje_ventas " +
            "FROM " +
            "   Order OG " +
            "   JOIN OG.details DET " +
            "   JOIN DET.dish D " +
            "WHERE " +
            "   OG.chef.id = :chefId " +
            "GROUP BY " +
            "   D.name")
    List<Object[]> porcentajesDePlatosVendidoPorChef(@Param("chefId") Long chefId);

}
