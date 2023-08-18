package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Dish dish;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Order order;
}
