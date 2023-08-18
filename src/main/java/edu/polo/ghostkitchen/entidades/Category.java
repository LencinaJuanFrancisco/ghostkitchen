package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 200, message = "{0} is too long.")
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    Set<Dish> dishes;

}
