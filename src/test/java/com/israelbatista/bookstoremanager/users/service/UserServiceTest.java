package com.israelbatista.bookstoremanager.users.service;


import com.israelbatista.bookstoremanager.users.builder.UserDTOBuilder;
import com.israelbatista.bookstoremanager.users.dto.MessageDTO;
import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import com.israelbatista.bookstoremanager.users.entity.User;
import com.israelbatista.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.israelbatista.bookstoremanager.users.exception.UserNotFoundException;
import com.israelbatista.bookstoremanager.users.mapper.UserMapper;
import com.israelbatista.bookstoremanager.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
        when(passwordEncoder.encode(expectedCreatedUser.getPassword())).thenReturn(expectedCreatedUser.getPassword());
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

    @Test
    void whenValidUserIsInformedThenItShouldBeDeleted() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDeletedUser = userMapper.toModel(expectedDeletedUserDTO);

        when(userRepository.findById(expectedDeletedUserDTO.getId())).thenReturn(Optional.of(expectedDeletedUser));
        doNothing().when(userRepository).deleteById(expectedDeletedUserDTO.getId());

        userService.delete(expectedDeletedUserDTO.getId());

        verify(userRepository, times(1)).deleteById(expectedDeletedUserDTO.getId());
    }

    @Test
    void whenInvalidUserIdIsInformedThenAnExceptionItShouldBeThrown() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        when(userRepository.findById(expectedDeletedUserDTO.getId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.delete(expectedDeletedUserDTO.getId()));
    }

    @Test
    void whenExistingUserIsInformedThenItShouldBeUpdated() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("israelupdate");
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);

        String expectedCreationMessage = "User israelupdate successfully updated";

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.of(expectedUpdatedUser));
        when(passwordEncoder.encode(expectedUpdatedUser.getPassword())).thenReturn(expectedUpdatedUser.getPassword());
        when(userRepository.save(expectedUpdatedUser)).thenReturn(expectedUpdatedUser);

        MessageDTO successUpdatedMessage = userService.update(expectedUpdatedUser.getId(), expectedUpdatedUserDTO);

        assertThat(successUpdatedMessage.getMessage(), is(equalToObject(expectedCreationMessage)));
    }

    @Test
    void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("israelupdate");
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.update(expectedUpdatedUser.getId(), expectedUpdatedUserDTO));
    }
}
