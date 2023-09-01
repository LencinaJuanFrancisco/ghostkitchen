package edu.polo.ghostkitchen.dto;

import edu.polo.ghostkitchen.validations.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Confirm
public class CategoryDto {

    @NotNull
    @NotEmpty(message = "Ingrese una categoría")
    private String category;

    @NotNull
    @NotEmpty(message = "Ingrese una descripción")
    private String description;

}
