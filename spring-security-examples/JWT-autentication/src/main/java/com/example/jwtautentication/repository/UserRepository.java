package com.example.jwtautentication.repository;

import com.example.jwtautentication.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByUsername(String username);

}
