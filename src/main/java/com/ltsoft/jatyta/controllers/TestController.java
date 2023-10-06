package com.ltsoft.jatyta.controllers;

import com.ltsoft.jatyta.models.User;
import com.ltsoft.jatyta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public List<User> userAccess()
  {
    return userRepository.findAll();
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }

  @DeleteMapping("/user/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUser(@PathVariable long id) { userRepository.deleteById(id); }

  @GetMapping("/user/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Optional<User> getUserById(@PathVariable long id) { return userRepository.findById(id); }

  @PutMapping("/user/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
    User user = userRepository.findById(id).orElseThrow(() ->
            new RuntimeException("User not found with id: " + id));

    user.setFirstname(userDetails.getFirstname());
    user.setLastname(userDetails.getLastname());
    user.setEmail(userDetails.getEmail());
    user.setPhone(userDetails.getPhone());

    userRepository.save(user);
    return ResponseEntity.ok(user);
  }
}
