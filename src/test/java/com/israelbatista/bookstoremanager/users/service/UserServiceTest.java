package com.israelbatista.bookstoremanager.users.service;


import com.israelbatista.bookstoremanager.users.builder.UserDTOBuilder;
import com.israelbatista.bookstoremanager.users.dto.MessageDTO;
import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import com.israelbatista.bookstoremanager.users.entity.User;
import com.israelbatista.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.israelbatista.bookstoremanager.users.mapper.UserMapper;
import com.israelbatista.bookstoremanager.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User IBatistaSantos successfully created";

        String expectedUserEmail = expectedCreatedUserDTO.getEmail();
        String expectedUserUsername = expectedCreatedUserDTO.getUsername();

        when(userRepository.findByEmailOrUsername(expectedUserEmail, expectedUserUsername)).thenReturn(Optional.empty());

        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));

    }

    @Test
    void whenExistingUserIsInformedThenAnExceptionShouldBeThrows() {
        UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);

        String expectedUserEmail = expectedDuplicatedUserDTO.getEmail();
        String expectedUserUsername = expectedDuplicatedUserDTO.getUsername();

        when(userRepository.findByEmailOrUsername(expectedUserEmail, expectedUserUsername))
                .thenReturn(Optional.of(expectedDuplicatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));

    }
}
