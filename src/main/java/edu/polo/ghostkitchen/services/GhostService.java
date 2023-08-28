package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.entidades.Ghosts;
import edu.polo.ghostkitchen.entidades.Ghosts.GhostRole;
import edu.polo.ghostkitchen.repositories.*;
import jakarta.transaction.Transactional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class GhostService implements UserDetailsService {

    @Autowired
    private GhostsRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder codificator;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Ghosts user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return buildUser(user, authorities);
    }



      private User buildUser(Ghosts user, Collection<? extends GrantedAuthority> authorities) {
        
        return new User(user.getEmail(), user.getPassword(), authorities);
    } 
    
    public List<GrantedAuthority> buildAuthorities(Ghosts.GhostRole role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

  

    @Transactional
    public void register(Ghosts user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese Email");
        }
        user.setPassword(codificator.encode(user.getPassword()));
        // Asignar un rol predeterminado en el momento del registro si es necesario.
        if (user.getRole() == null) {
            user.setRole(Ghosts.GhostRole.Cliente);
        }
        userRepository.save(user);
    }

    // Resto de los métodos de tu servicio...

}
