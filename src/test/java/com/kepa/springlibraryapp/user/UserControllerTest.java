package com.kepa.springlibraryapp.user;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.order.OrderDetails;
import com.kepa.springlibraryapp.verification.Token;
import com.kepa.springlibraryapp.verification.TokenNotFoundException;
import com.kepa.springlibraryapp.verification.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)

class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    Model model;

    @Mock
    TokenRepository tokenRepository;

    @Mock
    BindingResult bindingResult;

    @Mock
    User user;


    @InjectMocks
    UserController controller;

    List<UserDto> users = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        users.add(new UserDto());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void homeTest() {
        //given
        given(userService.findAll()).willReturn(users);

        //when
        String view = controller.home(model,null);

        //then
        then(userService).should().findAll();
        then(model).should().addAttribute(anyString(), any());
        assertThat("user").isEqualToIgnoringCase(view);
    }


    @Test
    void homeControllerTest() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user"));
    }
    @Test
    void homeControllerWithParamTest() throws Exception {
        mockMvc.perform(get("/users").param("text","test"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user"));
    }
    @Test
    void registerControllerTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("registerForm"));
    }

    @Test
    void addValidUserTest() throws Exception {
        mockMvc.perform(post("/register")
                    .param("firstname","John")
                    .param("lastname","Doe")
                    .param("email","mail@test.com")
                    .param("password","not empty"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerSuccess"));
    }

    @Test
    void addUserNotValidEmailTest() throws Exception {
        mockMvc.perform(post("/register")
                .param("firstname","John")
                .param("lastname","Doe")
                .param("email","mail")
                .param("password","not empty"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"));

    }


    @Test
    void singup() {
        //given
        Token token = new Token();
        token.setAppUser(user);
        Optional<Token> optionalToken = Optional.of(token);
        given(tokenRepository.findByValue("test")).willReturn(optionalToken);

        //when
        String view = controller.singup("test");

        //then
        then(user).should().setEnabled(true);
        then(userService).should().save(user);
        assertThat("activationSuccess").isEqualToIgnoringCase(view);
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }
}