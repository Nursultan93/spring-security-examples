package com.example.basic.access.autentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration {


  private static String [] URLS= new String[]{
      "/v1/api/all",
      "/v1/api/register"
  };

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

   return http
       .csrf(AbstractHttpConfigurer::disable)
       .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            request -> request.requestMatchers(URLS).permitAll()
                .requestMatchers("/v1/api/user").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/v1/api/manager").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/v1/api/admin").hasAnyRole("ADMIN")
                .requestMatchers("/v1/api/operator").hasAnyRole("OPERATOR")
                .anyRequest().authenticated()
        )
       .formLogin(Customizer.withDefaults())
       .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setPasswordEncoder(passwordEncoder);
    authProvider.setUserDetailsService(userDetailsService);
    return new ProviderManager(authProvider);
  }

}
