package edu.polo.ghostkitchen.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ghosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field is required.")
    @Size(max = 30, message = "{0} is too long.")
    private String name;

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

    @Enumerated(EnumType.STRING)
    private GhostRole role;

    @NotNull
    @NotBlank(message = "This field is required.")
    private String password;

    @Column(name = "`rank`")
    private float rank;

    private long history;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "users")
    Set<Chef> chefs;

    @OneToMany(mappedBy = "users")
    Set<Delivery> deliveries;

    @OneToMany(mappedBy = "users")
    Set<Client> clients;


    public enum GhostRole {
        Administrador,
        Chef,
        Cliente,
        Delivery,
    }
}