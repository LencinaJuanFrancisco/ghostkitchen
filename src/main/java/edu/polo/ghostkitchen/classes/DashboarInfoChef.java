package edu.polo.ghostkitchen.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.polo.ghostkitchen.repositories.ChefRepository;
import edu.polo.ghostkitchen.repositories.ClientRepository;
import edu.polo.ghostkitchen.repositories.DeliveryRepository;
import edu.polo.ghostkitchen.repositories.DetailRepository;
import edu.polo.ghostkitchen.repositories.DishRepository;
import edu.polo.ghostkitchen.repositories.GhostsRepository;
import edu.polo.ghostkitchen.repositories.OrderRepository;

@Controller
public class DashboarInfoChef {
    private final OrderRepository orderRepository;
 
    private final DishRepository dishRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public DashboarInfoChef(OrderRepository orderRepository, GhostsRepository ghostsRepository,
            ClientRepository clientRepository,
            ChefRepository chefRepository,
            DeliveryRepository deliveryRepository,
            DishRepository dishRepository,
            DetailRepository detailRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.detailRepository = detailRepository;
    }

    // catidad de ordenes vendidad
    // SELECT chef_id, COUNT(*) AS total_ordenes FROM ORDER_GHOST WHERE chef_id = 1;
    public Long cantidadDeOrdenesPorChef(Long chefId) {
        return orderRepository.cantidadDeOrdenesPorChef(chefId);
    }


    // cantidad de platos vendido
    // SELECT OG.chef_id, SUM(D.cantidad) AS total_dish_vendidos FROM ORDER_GHOST OG
    // JOIN DETAIL D ON OG.id = D.order WHERE OG.chef_id = 1 GROUP BY OG.chef_id;
    public Integer catidadDePlatosVendidosPorChef(Long chefId){
        return orderRepository.cantidadDePlatosVendidosPorChef(chefId);
    }

    // plato MAS Vendido
    // SELECT d.dish_id, dish.name, SUM(d.cantidad) AS totalCantidad 
    // FROM Detail d JOIN Dish dish ON d.dish_id = dish.id0
    // WHERE d.order IN 
    // ( SELECT id FROM order_ghost WHERE chef_id = 1 ) 
    //GROUP BY d.dish_id, dish.name ORDER BY totalCantidad DESC LIMIT 1;
    public Object platoMasVendidoPorChef(Long chefId){
        return detailRepository.platoMasVendidoPorChef(chefId);
    }


    // Plato MENOS vendido
   // ( SELECT id FROM order_ghost WHERE chef_id = 1 ) 
    //GROUP BY d.dish_id, dish.name ORDER BY totalCantidad;
    public Object platoMenosVendidoPorChef(Long chefId){
        return detailRepository.platoMenosVendidoPorChef(chefId);
    }

    // Plato MAYOR Ranking
    // SELECT D.id AS dish_id, D.name AS nombre_plato, MAX(D.rank) AS max_rank FROM
    // ORDER_GHOST OG JOIN DETAIL DET ON OG.id = DET.order JOIN DISH D ON
    // DET.dish_id = D.id WHERE OG.chef_id = 1 GROUP BY D.id, D.name ORDER BY
    // max_rank DESC;
    // devuelve el el listado ordenano de mayor a menor rank
    public Object paltoMayorRankingPorChef(Long ChefId){
        return dishRepository.platoMayorRankinPorChef(ChefId);
    }

    // Plato MENOR ranking
    // SELECT D.id AS dish_id, D.name AS nombre_plato, MAX(D.rank) AS max_rank FROM
    // ORDER_GHOST OG JOIN DETAIL DET ON OG.id = DET.order JOIN DISH D ON
    // DET.dish_id = D.id WHERE OG.chef_id = 1 GROUP BY D.id, D.name ORDER BY
    // max_rank;
    // devuelve el listado de menor a mayor
    public Object paltoMenorRankingPorChef(Long ChefId){
        return dishRepository.platoMenorRankinPorChef(ChefId);
    }


    // Promedio de Ranking
    // SELECT AVG(D.rank) AS promedio_rank FROM ORDER_GHOST OG JOIN DETAIL DET ON
    // OG.id = DET.order JOIN DISH D ON DET.dish_id = D.id WHERE OG.chef_id = 1;
    public Object promedioDeRankingDePlatosPorChef(Long ChefId){
        return dishRepository.promedioDeRankingDePlatosPorChef(ChefId);
    }

    // Total de ventas
    // SELECT SUM(OG.price) AS total_ventas FROM ORDER_GHOST OG WHERE OG.chef_id =
    // 1;
    public Double totalVentaPorChef(Long chefId) {
        return orderRepository.totalVentaPorChef(chefId);
    }

    // platos representados por Porcentajes
   // SELECT D.name AS nombre_plato, COUNT(*) AS total_ordenes_por_plato, (COUNT(*) * 100) / ( SELECT COUNT(*) FROM DETAIL DET WHERE DET.order IN ( SELECT id FROM ORDER_GHOST OG WHERE OG.chef_id = 1 ) ) AS porcentaje_ventas FROM ORDER_GHOST OG JOIN DETAIL DET ON OG.id = DET.order JOIN DISH D ON DET.dish_id = D.id WHERE OG.chef_id = 1 GROUP BY D.name;
   public List<Object[]> porcentajesDePlatosVendidoPorChef(Long chefId) {
    return orderRepository.porcentajesDePlatosVendidoPorChef(chefId);
}


}
