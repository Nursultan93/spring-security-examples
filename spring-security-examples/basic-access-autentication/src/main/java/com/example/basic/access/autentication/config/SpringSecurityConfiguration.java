package com.example.basic.access.autentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {


  private static String [] URLS= new String[]{
      "/v1/api/user",
      "/v1/api/client"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

   return http
        .authorizeHttpRequests(
            request -> request.requestMatchers(URLS).permitAll()
                .anyRequest().authenticated()
        )
       .formLogin(Customizer.withDefaults())
       .build();
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
    UserDetails userDetails =  User
        .withUsername("salam")
        .password(passwordEncoder().encode("54321")) // {noop}
        .build();

    return new InMemoryUserDetailsManager(userDetails);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
