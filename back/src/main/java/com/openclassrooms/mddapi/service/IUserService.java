package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getUserByEmail(String email);

    void save(LoginRequestDTO loginRequestDTO) throws Exception;
}
