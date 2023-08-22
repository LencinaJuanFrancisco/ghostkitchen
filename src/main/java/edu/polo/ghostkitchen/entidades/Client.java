package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Ghost user;

    @OneToMany(mappedBy = "client")
    Set<Order> orders;
}
