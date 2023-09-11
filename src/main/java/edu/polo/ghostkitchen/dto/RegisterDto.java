package edu.polo.ghostkitchen.dto;

import edu.polo.ghostkitchen.validations.*;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import java.util.Date;
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
    
     private String phone;

    @NotNull
    @NotEmpty(message = "Ingrese un nombre")
    private String name;

    @NotNull
    @NotEmpty(message = "Ingrese una dirección")
    private String address;

    @Temporal(TemporalType.DATE)
    private Date birthday;

}
