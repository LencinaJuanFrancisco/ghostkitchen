package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 30, message = "{0} is too long.")
    private String name;
    private String description;
    private float price;
    
    private boolean disponibility;
    private float rank;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Chef chef;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Category category;
    
    @OneToMany(mappedBy = "dish")
    Set<Detail> details;
}
