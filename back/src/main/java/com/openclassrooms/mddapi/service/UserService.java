package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * Get a UserDTO by its Email. The UserDTO will not contain the password for safety purpose.
     * @param email
     * @return a UserDTO.
     */
    @Override
    public UserDTO getUserDTOByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setName(user.getName());
        userDTO.setTopics(user.getTopics());
        return userDTO;
    }

    /**
     * Save a user in the database. The password is encrypted with Bcrypt.
     * @param loginRequestDTO
     * @throws Exception
     */
    @Override
    public void save(LoginRequestDTO loginRequestDTO) throws Exception {
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

    /**
     * UserDetails needed for Spring Security.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found."));
        return new LoginRequestDTO(user.getEmail(), user.getPassword(), user.getName());
    }

    /**
     * Update a user: fields that can be updated: name and/or email and/or password.
     * A check is done to compare the name and the new name, if there are different, then the name is changed.
     * The same is done for email.
     * For password, we use Bcrypt method to compare the new password with the hash saved in database.
     * @param email
     * @param loginRequestDTO
     */
    @Override
    public void updateUser(String email, LoginRequestDTO loginRequestDTO) {
    User user = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found."));
        if(!user.getName().equals(loginRequestDTO.getName()) && loginRequestDTO.getName() != null) {
            user.setName(loginRequestDTO.getName());
        }
        if(!user.getEmail().equals(loginRequestDTO.getEmail()) && loginRequestDTO.getEmail() != null) {
            user.setEmail(loginRequestDTO.getEmail());
        }
        if(!(passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
                && loginRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(loginRequestDTO.getPassword()));
        }
        userRepository.save(user);
    }

    /**
     * Unsubscribe to a topic.
     * @param topic the topic to remove from subscriptions.
     * @param email the email of the user who wants to unsubscribe.
     * @return a String to inform the user if the topic is removed or not.
     */
    @Override
    public String unsubscribe(Topic topic, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        boolean isTopicInUserTopics = user.getTopics().stream().anyMatch(t -> t.getId() == topic.getId());
        if(isTopicInUserTopics) {
            user.getTopics().remove(topic);
            userRepository.save(user);
            return "Sujet "+topic.getName()+" enlevé des abonnements.";
        } else {
            return("Sujet "+topic.getName()+" ne fait pas partie des abonnements de l'utilisateur "+email+".");
        }
    }

    /**
     * Subscribe to a Topic.
     * @param topic the topic to add to subscriptions.
     * @param email the email of the user who wants to subscribe.
     * @return a String to inform the user if the subscription is done or not.
     */
    @Override
    public String subscribe(Topic topic, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        boolean isTopicInUserTopics = user.getTopics().stream().anyMatch(t -> t.getId() == topic.getId());
        if(!isTopicInUserTopics) {
            user.getTopics().add(topic);
            userRepository.save(user);
            return "Sujet "+topic.getName()+" ajouté aux abonnements.";
        } else {
            return("Sujet "+topic.getName()+" fait déjà partie des abonnements de l'utilisateur "+email+".");
        }
    }
}
