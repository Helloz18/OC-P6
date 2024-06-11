package com.openclassrooms.mddapi.model;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
}
