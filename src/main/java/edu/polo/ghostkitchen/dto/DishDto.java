package edu.polo.ghostkitchen.dto;

import edu.polo.ghostkitchen.entidades.Category;
import edu.polo.ghostkitchen.validations.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Confirm
public class DishDto {

    @NotNull
    @NotEmpty(message = "Ingrese un nombre para el plato")
    private String name;

    private String description;

    private Category category;

    private Long price;

    private float rank;

    private boolean disponibility;

    private String image;
}
