package com.example.jwtautentication.service;

import com.example.jwtautentication.dto.LoginRequest;
import com.example.jwtautentication.model.User;
import com.example.jwtautentication.repository.UserRepository;
import com.example.jwtautentication.util.JwtUtil;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  //private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  public User create(User user) {
    ///user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  public String autenticate(LoginRequest request) {
    autenticate(request.getUsername(), request.getPassword());

    final UserDetails userDetails = loadUserByUsername(request.getUsername());

    final String token = jwtUtil.generateToken(userDetails);

    return token;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findUserByUsername(username)
        .orElseThrow(()-> new RuntimeException("User not found!"));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList())
    );
  }

  private void autenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}
