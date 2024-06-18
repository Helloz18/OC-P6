package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User controller", description = "Controller used to manage a user: get, update.")
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile")
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
            @Parameter(description = "Bearer token", example="Bearer eyJhbGciOJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearer) throws Exception {
        if(bearer == null) {
            System.out.println("test");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        } else {
            //The bearer token from the header is given with "bearer " before the actual token.
            //we remove this part to just give the token to the service.
            String email = jwtService.getEmailByToken(bearer.substring(7));
            System.out.println("email "+email);
            UserDTO userDTO = userService.getUserDTOByEmail(email);
            System.out.println("user DTO " + userDTO.getTopics());
            return ResponseEntity.ok(userDTO);
        }
    }

}
