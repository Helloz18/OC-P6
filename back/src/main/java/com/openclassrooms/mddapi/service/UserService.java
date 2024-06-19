package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.Topic;
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
    public UserDTO getUserDTOByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setName(user.getName());
        userDTO.setTopics(user.getTopics());
        return userDTO;
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

    @Override
    public void updateUser(String email, LoginRequestDTO loginRequestDTO) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        if(!user.getName().equals(loginRequestDTO.getName()) && loginRequestDTO.getName() != null) {
            user.setName(loginRequestDTO.getName());
        }
        if(!user.getEmail().equals(loginRequestDTO.getEmail()) && loginRequestDTO.getEmail() != null) {
            user.setEmail(loginRequestDTO.getEmail());
        }
        if(!user.getPassword().equals(loginRequestDTO.getPassword()) && loginRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(loginRequestDTO.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public String modifySubscription(Topic topic, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        boolean isTopicInUserTopics = user.getTopics().stream().anyMatch(t -> t.getId() == topic.getId());
        if(isTopicInUserTopics) {
            user.getTopics().remove(topic);
            userRepository.save(user);
            return "Sujet "+topic.getName()+" enlevé des abonnements.";
        } else {
            user.getTopics().add(topic);
            userRepository.save(user);
            return "Sujet "+topic.getName()+" ajouté aux abonnements.";
        }
    }
}
