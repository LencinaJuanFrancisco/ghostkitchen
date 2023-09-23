package edu.polo.ghostkitchen.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import edu.polo.ghostkitchen.entidades.Chef;
import edu.polo.ghostkitchen.entidades.Dish;
import edu.polo.ghostkitchen.entidades.Ghosts;
import edu.polo.ghostkitchen.repositories.ChefRepository;
import edu.polo.ghostkitchen.repositories.ClientRepository;
import edu.polo.ghostkitchen.repositories.DeliveryRepository;
import edu.polo.ghostkitchen.repositories.DetailRepository;
import edu.polo.ghostkitchen.repositories.DishRepository;
import edu.polo.ghostkitchen.repositories.GhostsRepository;
import edu.polo.ghostkitchen.repositories.OrderRepository;


@Controller
public class DashboarInfo {
     private final OrderRepository orderRepository;
    private final GhostsRepository ghostsRepository;
    private final ClientRepository clientRepository;
    private final ChefRepository chefRepository;
    private final DeliveryRepository deliveryRepository;
    private final DishRepository dishRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public DashboarInfo(OrderRepository orderRepository, GhostsRepository ghostsRepository,
            ClientRepository clientRepository,
            ChefRepository chefRepository,
            DeliveryRepository deliveryRepository,
            DishRepository dishRepository,
            DetailRepository detailRepository
            ) {
         this.orderRepository = orderRepository;
        this.ghostsRepository = ghostsRepository;
        this.clientRepository = clientRepository;
        this.chefRepository = chefRepository;
        this.deliveryRepository = deliveryRepository;
        this.dishRepository = dishRepository;
         this.detailRepository = detailRepository;
    }

    public int getCantidadDeOrdenes() {
        return orderRepository.getCantidadDeOrdenes();
    }

    public double getTotalDeVentas() {
        return orderRepository.getTotalDeVentas();
    }

    public double getGanancia() {
        return orderRepository.getGanancia();
    }

    public long getCantidadDeUsuarios() {
        return ghostsRepository.count();
    }

    public long getCantidadDeCompradores() {
        return clientRepository.count();
    }

    public long getCantidadDeCocineros() {
        return chefRepository.count();
    }

    public long getCantidadDeDelivery() {
        return deliveryRepository.count();
    }

    public long getCantidadDePlatos() {
        return dishRepository.count();
    }

  
    public Object[] getPlatoMasVendido() {
        List<Object[]> result = detailRepository.platoMasVendido();
        if (!result.isEmpty()) {
            Object[] platoMasVendido = result.get(0);
            Long platoId = (Long) platoMasVendido[0]; // 
            Dish plato = dishRepository.findById(platoId).orElse(null);
            if (plato != null) {
                platoMasVendido[0] = plato.getName(); // Reemplaza el ID del plato con el nombre
                return platoMasVendido;
            }
        }
        return null;
    }
    
    
    public Object[] getCocinaMasVendida() {
        List<Object[]> result = orderRepository.chefMasOrdenes();
        if (!result.isEmpty()) {
            Object[] cocinaMasVendida = result.get(0);
            Long cocinaId = (Long) cocinaMasVendida[0]; // Supongo que el ID de la cocina está en la posición 0
            Chef cocina = chefRepository.findById(cocinaId).orElse(null);
            if (cocina != null) {
                cocinaMasVendida[0] = cocina.getUser().getName(); // Reemplaza el ID de la cocina con el nombre
                return cocinaMasVendida;
            }
        }
        return null;
    }
      
    
    
    
    
    
    // public Object[] getClienteQueMasCompro() {
    //     List<Object[]> result = orderRepository.clienteMasCompro();
    //     if (!result.isEmpty()) {
    //         return result.get(0);
    //     }
    //     return null;
    // }
    public Object[] getClienteQueMasCompro() {
        List<Object[]> result = orderRepository.clienteMasCompro();
        if (!result.isEmpty()) {
            Object[] clienteQueMasCompro = result.get(0);
            Long clienteId = (Long) clienteQueMasCompro[0]; 
            Ghosts cliente = ghostsRepository.findById(clienteId).orElse(null);
            if (cliente != null) {
                clienteQueMasCompro[0] = cliente.getName(); // Reemplaza el ID del cliente con el nombre
                return clienteQueMasCompro;
            }
        }
        return null;
    }
    
    
    
    
    

    // catidaded de ordenes
    // la longitud del array

    // total de ventas
    // SQL SELECT SUM(price) AS suma_total FROM order_ghost ;

    // ganacia , calcular el 10% de total de ventas
    // SQL SELECT SUM(price) AS suma_total, SUM(price) * 0.10 AS
    // ganancia_10_porcentaje FROM order_ghost;

    // cantidad de usuarios
    // la longitud de ghosts

    // cantidad de compradores
    // tarer la logitud de client

    // catidad de cocineros
    // tarer la longitud de chef

    // cantidad de delivery
    // traer la longitud de delivery

    // cantidad de platos
    // traer la longitud de dish

    // plato mas vendido, esta consulta devuelve el dish_id con la cantidad de veces
    // que se vendio
    // SQL SELECT dish_id, COUNT(dish_id) AS cantidad FROM DETAIL GROUP BY dish_id
    // ORDER BY cantidad DESC LIMIT 1;

    // la cocina que mas vendio, devuelve el id de chef con la cantidad de ordenes
    // realizadas
    // SELECT chef_id, COUNT(*) AS ventas_totales FROM order_ghost GROUP BY chef_id
    // ORDER BY ventas_totales DESC LIMIT 1;

    // el usuario que mas compro, devuelve el id del usuario que mas vendio con la
    // cantidad de ordenes realizadas
    // SELECT client_id, COUNT(*) AS compras_totales FROM order_ghost GROUP BY
    // client_id ORDER BY compras_totales DESC LIMIT 1;
}
