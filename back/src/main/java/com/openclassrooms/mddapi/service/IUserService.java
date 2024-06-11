package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getUserByEmail(String email);

    User save(UserDTO userDTO);
}
