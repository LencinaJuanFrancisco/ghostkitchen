package edu.polo.ghostkitchen.entidades;

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

    private String detalle;
    private int cantidad;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Dish dish;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "`order`")
    private Order order;
}
