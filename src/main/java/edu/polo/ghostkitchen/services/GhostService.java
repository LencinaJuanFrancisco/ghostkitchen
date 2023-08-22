package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
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
    private GhostRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder codificator;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Ghost user = userRepository.findByEmail(email);
        List<GrantedAuthority> ga = buildAuthorities(user.getRole());
        return buildUser(user, ga);
    }
    
    public User buildUser(Ghost user, List<GrantedAuthority> ga) {
        return new User(user.getEmail(), user.getPassword(), ga);
    }

    //donde dice Ghost va Rol, hay que ver
    public List<GrantedAuthority> buildAuthorities(Ghost.UserRole role) {
        List<GrantedAuthority> ga = new ArrayList<>();
        ga.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return ga;
    }
    
    @Transactional
    public void register(Ghost user) {
        if (userRepository.existsByEmail(user.getEmail())) 
            throw new IllegalArgumentException("Ya existe un usuario con ese Email");
        user.setPassword(codificator.encode(user.getPassword()));
       userRepository.save(user);
    }

    public List<Ghost> getAll() {
        List<Ghost> lista = new ArrayList<Ghost>();
        userRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Ghost getById(Long id) {
        return userRepository.findById(id).get();
    }

    public void save(Ghost user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
    

