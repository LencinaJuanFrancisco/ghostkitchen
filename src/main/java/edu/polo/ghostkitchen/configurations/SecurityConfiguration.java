package edu.polo.ghostkitchen.configurations;

import edu.polo.ghostkitchen.services.GhostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private GhostService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Bean
<<<<<<< HEAD
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/categories", "/home", "/menu", "/register", "/dishdetail", "/createcategory", "/perfil", "/registerKitchen", "/registerDelivery", "/css/**", "/images/**", "/createDish").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                )
                .logout((logout) -> logout.permitAll());
=======
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/","/categories", "/home","/orden", "/menu","/register","/dishdetail", "/createcategory","/perfil","/registerKitchen", "/registerDelivery", "/css/**", "/images/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.loginProcessingUrl("/logincheck")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());
>>>>>>> devs

        return http.build();

    }
}
