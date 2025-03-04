package com.delivery.app.online_delivery_application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.delivery.app.online_delivery_application.wrapperClass.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

            .csrf(customizer -> customizer.disable())
            .securityContext(context -> context
                .requireExplicitSave(false)
            )
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/signup", "/error", "/resources/**", "/js/**", "/css/**", "/webjars/**", "/views/**").permitAll()
            // .requestMatchers("/api/customers/products").permitAll() // Allow browsing products
            // .requestMatchers("/api/customers/cart/**").hasAuthority("CUSTOMER") // Only customers can access cart
            // .requestMatchers("/api/admin/**").hasRole("ADMIN") // Restrict admin routes
            // .requestMatchers("/api/vendor/**").hasRole("VENDOR") // Restrict vendor routes
            .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .httpBasic(customizer -> customizer.disable())
            .logout(logout -> logout    
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
            // .csrf(csrf -> csrf
            //     .ignoringRequestMatchers("/login", "/logout","/signup","/api/**") // Adjust paths based on your needs
            // );

        return http.build();
    }

}
