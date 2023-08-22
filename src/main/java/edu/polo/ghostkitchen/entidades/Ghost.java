package edu.polo.ghostkitchen.entidades;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ghost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 30, message = "{0} is too long.")
    private String name;
    private String lastName;
    private String cellphone;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 100, message = "Address is too long.")
    private String address;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 255, message = "{0} is too long.")
    @Column(unique = true)
    @Email
    private String email;

    private UserRole role;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 45, message = "{0} is too long.")
    private String password;

    private float rank;
    private long history;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "user")
    Set<Chef> chefs;

    @OneToMany(mappedBy = "user")
    Set<Delivery> deliveries;
    
    @OneToMany(mappedBy = "user")
    Set<Client> clients;

    public enum UserRole {
        Admin,
        Delivery,
        Chef,
        Client,
    }

}
