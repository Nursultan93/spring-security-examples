package com.example.basic.access.autentication.service;

import com.example.basic.access.autentication.model.Role;
import com.example.basic.access.autentication.model.User;
import com.example.basic.access.autentication.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User registerUser(User user) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    var user = userRepository.findUserByUsername(username)
        .orElseThrow(() -> new RuntimeException("User nor found!"));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        getRoles(user.getRoles())
    );
  }

  private static List<SimpleGrantedAuthority> getRoles(Set<Role> roles) {
    List<SimpleGrantedAuthority> list = new ArrayList<>();

    for (Role role : roles) {
      list.add(new SimpleGrantedAuthority(role.getRoleName()));
    }

    return list;
  }
}
