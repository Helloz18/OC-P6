package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.LoginRequest;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private JwtService jwtService;

    private IUserService userService;

    private AuthenticationManager authenticationManager;

    AuthController(
            JwtService jwtService,
                   IUserService userService,
                   AuthenticationManager authenticationManager
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if(userService.getUserByEmail(loginRequest.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user unauthorized");
        } else {
            String token = createToken(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(token);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        String token = createToken(userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.ok(token);
    }

    /**
     * Method to create the token for login and registration.
     * @param email
     * @param password
     * @return
     */
    private String createToken(String email, String password) {
        String token = "";
        Authentication
                authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated()) {
             token = jwtService.generateToken(authentication);
            return token;
        }
        return token;
    }

}
