package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Order_ghost")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int state;
    private float price;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Chef chef;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Delivery delivery;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Detail> details = new HashSet<>();


    // Método para agregar un plato a la orden
    public void addDetail(Detail detail) {
        detail.setOrder(this);
        details.add(detail);
    }

    // Método para eliminar un palto de la orden por ID
    public void removeDetailById(Long detailId) {
        details.removeIf(detail -> detail.getId().equals(detailId));
    }
}
