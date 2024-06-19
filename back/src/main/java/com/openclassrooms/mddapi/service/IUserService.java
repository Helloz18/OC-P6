package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getUserByEmail(String email);

    UserDTO getUserDTOByEmail(String email);

    void save(LoginRequestDTO loginRequestDTO) throws Exception;

    void updateUser(String email, LoginRequestDTO loginRequestDTO);

    String modifySubscription(Topic topic, String email);
}
