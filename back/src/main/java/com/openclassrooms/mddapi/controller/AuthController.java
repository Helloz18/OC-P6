package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.LoginRequest;
import com.openclassrooms.mddapi.model.ResponseMessage;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user is unknown.");
        } else {
            String token = createToken(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(token);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register in the application. The user is saved in the database.",
            description = "Give a new email, name and password, then a token will be generated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a JWT token.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "One or several of the mandatory fields is/are empty.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "401", description = "Email already exists in database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })

    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws Exception {
        if(userDTO.getName() == null || userDTO.getEmail() == null || userDTO.getPassword() == null
                || userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("One or several of the mandatory fields is/are empty."));
        } else {
            try {
                userService.save(userDTO);
            } catch(Exception e) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(e.getMessage()));
            }
        }
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
