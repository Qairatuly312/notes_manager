package spring.notes.controllers;

import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.notes.DTO.UserDTO;
import spring.notes.repositories.UserRepository;
import spring.notes.util.secure.JwtUtil;
import spring.notes.entity.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final PasswordEncoder encoder;
    final JwtUtil jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getName(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid UserDTO userDTO) {
        if (userRepository.existsByName(userDTO.getName())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        User newUser = new User(null, userDTO.getEmail(), userDTO.getName(), encoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}