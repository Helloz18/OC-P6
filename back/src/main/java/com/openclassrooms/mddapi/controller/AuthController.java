package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.model.LoginRequest;
import com.openclassrooms.mddapi.model.ResponseMessage;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Auth controller", description = "Controller used to log in the application and to register a new user.")
@RequestMapping(value = "/api/auth")
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
    @Operation(summary = "Log in the application.",
            description = "Give a valid email and password, then a token will be generated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a JWT token.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "User unknown.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "401", description = "Bad credentials.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if(userService.getUserByEmail(loginRequest.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Vérifiez l'email et le mot de passe."));
        } else {

        Map<String, String> token = new HashMap<>();

           try {
                token.put("token", createToken(loginRequest.getEmail(), loginRequest.getPassword()));
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(e.getMessage()));
            }
            return ResponseEntity.ok(token);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register in the application. The user is saved in the database.",
            description = "Give a new email, name and password, then a token will be generated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a JWT token.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "One or several of the mandatory fields is/are empty.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "401", description = "Email already exists in database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<?> register(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        if(loginRequestDTO.getName() == null || loginRequestDTO.getEmail() == null || loginRequestDTO.getPassword() == null
                || loginRequestDTO.getName().isEmpty() || loginRequestDTO.getEmail().isEmpty() || loginRequestDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("One or several of the mandatory fields is/are empty."));
        } else {
            try {
                userService.save(loginRequestDTO);
            } catch(Exception e) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(e.getMessage()));
            }
        }
        Map<String, String> token = new HashMap<>();
        token.put("token", createToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        return ResponseEntity.ok(token);
    }

    /**
     * Method to create the token for login and registration.
     * @param email
     * @param password
     * @return
     */
    private String createToken(String email, String password) throws Exception {
        String token = "";

            Authentication
                    authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(email, password));
            if (authentication.isAuthenticated()) {
                token =
                        jwtService.generateToken(authentication);
                return token;
            }
        else {
                throw new Exception();
            }
    }

}
