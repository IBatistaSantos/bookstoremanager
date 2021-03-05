package com.israelbatista.bookstoremanager.users.service;

import com.israelbatista.bookstoremanager.users.dto.MessageDTO;
import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import com.israelbatista.bookstoremanager.users.entity.User;
import com.israelbatista.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.israelbatista.bookstoremanager.users.exception.UserNotFoundException;
import com.israelbatista.bookstoremanager.users.mapper.UserMapper;
import com.israelbatista.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final  static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MessageDTO create(UserDTO userDTO) {
        verifyExists(userDTO.getEmail(), userDTO.getUsername());
        User userToCreate = userMapper.toModel(userDTO);
        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        User createdUser = userRepository.save(userToCreate);

        return MessageDTO.builder()
                .message(String.format("User %s successfully created", createdUser.getUsername()))
                .build();
    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
        User foundUser = verifyAndGetIfExists(id);
        userToUpdateDTO.setId(foundUser.getId());


        User userToUpdate = userMapper.toModel(userToUpdateDTO);
        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        User updateUser = userRepository.save(userToUpdate);

        return MessageDTO.builder()
                .message(String.format("User %s successfully updated", updateUser.getUsername()))
                .build();
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        userRepository.deleteById(id);
    }

    private User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }

    public User verifyAndGetUserIfExists(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
