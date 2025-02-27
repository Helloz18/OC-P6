package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.ResponseMessage;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.ITopicService;
import com.openclassrooms.mddapi.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User controller", description = "Controller used to manage a user: get, update.")
@RequestMapping(value = "/api/profile")
public class UserController {

    private JwtService jwtService;
    private IUserService userService;
    private ITopicService topicService;

    public UserController(JwtService jwtService, IUserService userService, ITopicService topicService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping("")
    @Operation(summary = "Get information of the connected user.",
            description = "The user must be connected as we need the token in the header.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a UserDTO.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)))
    })
    public ResponseEntity<?> getConnectedUser(
            @Parameter(description = "Bearer token", example = "Bearer eyJhbGciOJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearer) {
        if (bearer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        } else {
            //The bearer token from the header is given with "bearer " before the actual token.
            //we remove this part to just give the token to the service.
            String
                    email =
                    jwtService.getEmailByToken(bearer.substring(7));
            UserDTO
                    userDTO =
                    userService.getUserDTOByEmail(email);
            return ResponseEntity.ok(userDTO);
        }
    }

    @PutMapping("/{email}")
    @Operation(summary = "Modify the connected user: name, email and password.",
            description = "The user must be connected as we need the token in the header.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user is modified in database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "404", description = "The user doesn't exist in the database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Invalid token.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "User tries to use a token not linked to his account",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "The email provided exists already for another user.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<ResponseMessage> updateUser(
            @Parameter(description = "Bearer token", example = "Bearer eyJhbGciOJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearer,
            @PathVariable String email, @RequestBody LoginRequestDTO loginRequestDTO
    ) {
       try {
           if (!email.equals(loginRequestDTO.getEmail())) {
               if (!userService.getUserByEmail(loginRequestDTO.getEmail()).isEmpty()) {
                   return ResponseEntity.status(HttpStatus.CONFLICT)
                           .body(new ResponseMessage(("Cet email est déjà utilisé dans l'application.")));
               }
           } else {
               if (userService.getUserByEmail(email).isEmpty()) {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ResponseMessage("L'utilisateur n'existe pas."));
               } else {
                   if (bearer == null) {
                       return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                               .body(new ResponseMessage("Token non valide."));
                   } else {
                       //The bearer token from the header is given with "bearer " before the actual token.
                       //we remove this part to just give the token to the service.
                       String
                               emailToken =
                               jwtService.getEmailByToken(bearer.substring(7));
                       //Verify that the token is linked to the user who wants modification
                       if (!emailToken.equals(email)) {
                           return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                   .body(new ResponseMessage(
                                           "L'utilisateur utilise un token non généré par ses credentials."));
                       }
                   }
                   userService.updateUser(email, loginRequestDTO);
               }
           }
           return ResponseEntity.ok(new ResponseMessage("User modifié et enregistré en base de données."));
       }catch (Exception e) {
           return ResponseEntity.badRequest().body(new ResponseMessage("Une erreur est survenue. Vérifiez vos entrées."));
       }
    }

    @PutMapping("/topic/{topicId}")
    @Operation(summary = "Unsubscribe to a subscription.",
            description = "The Id of the topic and the email of the user are mandatory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The subscription is removed.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "404", description = "The user or the topic doesn't exist in the database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<ResponseMessage> unsubscribe(
            @PathVariable Long topicId, @RequestParam String email) throws Exception {
        if (userService.getUserByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("L'utilisateur n'existe pas."));
        }
        try {
            Topic topic = topicService.findTopicById(topicId);
            String message = userService.unsubscribe(topic, email);
            return ResponseEntity.ok(new ResponseMessage(message));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        }
    }
}
