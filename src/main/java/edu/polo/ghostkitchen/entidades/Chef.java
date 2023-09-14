package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String web;
    private String schedules;
    private String description;
    
    @Column(name = "image")
    private String image;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Ghosts user;

    @OneToMany(mappedBy = "chef")
    Set<Dish> dishes;

    @OneToMany(mappedBy = "chef")
    Set<Order> orders;

}
