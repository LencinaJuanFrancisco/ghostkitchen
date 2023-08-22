package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 30, message = "{0} is too long.")
    private String licencePlate;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Ghost user;
    
    @OneToMany(mappedBy = "delivery")
    Set<Order> orders;
}
