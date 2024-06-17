package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserByEmail(String email) {
         return userRepository.findByEmail(email);
    }

    @Override
    public void save(LoginRequestDTO loginRequestDTO) throws Exception {
        log.info("Save new user: " + loginRequestDTO.getEmail());
        if (userRepository.findByEmail(loginRequestDTO.getEmail()).isPresent()) {
            throw new Exception("The email provided may be registered already: " + loginRequestDTO.getEmail());
        } else {
            User user = new User();
            user.setName(loginRequestDTO.getName());
            user.setEmail(loginRequestDTO.getEmail());
            user.setPassword(passwordEncoder.encode(loginRequestDTO.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return new LoginRequestDTO(user.getEmail(), user.getPassword(), user.getName());
    }
}
