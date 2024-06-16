package com.example.generatorsdiplomawork.config;

import com.example.generatorsdiplomawork.entities.UserRoles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/users").hasAuthority(UserRoles.ADMIN.getAuthority())
                        .requestMatchers("/aggregates/create").hasAuthority(UserRoles.ADMIN.getAuthority())
                        .requestMatchers("/aggregates/delete/*").hasAuthority(UserRoles.ADMIN.getAuthority())

                        .requestMatchers("/aggregates/edit/*", HttpMethod.GET.toString())
                        .hasAnyAuthority(UserRoles.USER.getAuthority(), UserRoles.ADMIN.getAuthority())
                        .requestMatchers("/aggregates/edit", HttpMethod.POST.toString())
                        .hasAnyAuthority(UserRoles.USER.getAuthority(), UserRoles.ADMIN.getAuthority())
                        .requestMatchers("/aggregates/edit/*", HttpMethod.POST.toString())
                        .hasAuthority(UserRoles.ADMIN.getAuthority())

                        .requestMatchers("/user/create").hasAuthority(UserRoles.ADMIN.getAuthority())

                        .requestMatchers("/*").hasAnyAuthority(UserRoles.USER.getAuthority(), UserRoles.ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .loginProcessingUrl("/process_login")
                        .failureUrl("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }


//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
//        return http.build();
//    }
}

