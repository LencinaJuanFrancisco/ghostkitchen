package edu.polo.ghostkitchen.dto;

import edu.polo.ghostkitchen.validations.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Confirm
@EmailUnic
public class RegisterDto {
    @NotNull
    @NotEmpty(message = "Ingrese una direccion de correo")
    @Email(message = "Ingrese una direccion de correo valida")
    private String email;
    
    @NotNull
    @NotEmpty(message = "Ingrese una contraseña")
    private String password;
    
    private String confirm;
    
    private String recaptcha;
}
