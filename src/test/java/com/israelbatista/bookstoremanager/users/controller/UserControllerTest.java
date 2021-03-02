package com.israelbatista.bookstoremanager.users.controller;


import com.israelbatista.bookstoremanager.users.builder.JwtRequestBuilder;
import com.israelbatista.bookstoremanager.users.builder.UserDTOBuilder;
import com.israelbatista.bookstoremanager.users.dto.JwtRequest;
import com.israelbatista.bookstoremanager.users.dto.JwtResponse;
import com.israelbatista.bookstoremanager.users.dto.MessageDTO;
import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import com.israelbatista.bookstoremanager.users.service.AuthenticationService;
import com.israelbatista.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.israelbatista.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USERS_API_URI = "/api/v1/users";
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    private JwtRequestBuilder jwtRequestBuilder;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        jwtRequestBuilder = JwtRequestBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreatedDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "User IBatistaSantos successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(expectedUserToCreatedDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USERS_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreatedDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithOutRequiredFieldThenBadRequestShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreatedDTO = userDTOBuilder.buildUserDTO();
        expectedUserToCreatedDTO.setUsername(null);

        mockMvc.perform(post(USERS_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreatedDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledThenNoContentShouldBeInformed() throws Exception {
        UserDTO expectedUserToDeleteDTO = userDTOBuilder.buildUserDTO();

        doNothing().when(userService).delete(expectedUserToDeleteDTO.getId());

        mockMvc.perform(delete(USERS_API_URI + "/" + expectedUserToDeleteDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenPUTIsCalledThenOkStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToUpdateDTO.setUsername("IBatiistaSantos");
        String expectedUpdateMessage = "User IBatiistaSantos successfully updated";

        MessageDTO expectedUpdateMessageDTO = MessageDTO.builder().message(expectedUpdateMessage).build();

        when(userService.update(expectedUserToUpdateDTO.getId(),expectedUserToUpdateDTO))
                .thenReturn(expectedUpdateMessageDTO);

        mockMvc.perform(put(USERS_API_URI + "/" + expectedUserToUpdateDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedUpdateMessage)));
    }

    @Test
    void whenPOSTIsCalledToAuthenticateUserThenOkShouldBeReturned() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.builderJwtRequest();
        JwtResponse expectedJwtToken = JwtResponse.builder().jwtToken("fakeToken").build();

        when(authenticationService.createAuthenticationToken(jwtRequest)).thenReturn(expectedJwtToken);

        mockMvc.perform(post(USERS_API_URI + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken", is(expectedJwtToken.getJwtToken())));
    }

    @Test
    void whenPOSTIsCalledToAuthenticateUserWithoutPasswordThenBadRequestShouldBeReturned() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.builderJwtRequest();
        jwtRequest.setPassword(null);


        mockMvc.perform(post(USERS_API_URI + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
                .andExpect(status().isBadRequest());
    }
}

