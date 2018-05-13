package com.sbt.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
public class UserControllerSecurityTest {

    private final String PASS = new BCryptPasswordEncoder().encode("pass");

    private final User STUB_USER = User.builder()
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .username("test user")
            .password(PASS)
            .roles(Collections.singleton(Role.USER))
            .privileges(Collections.singleton(Privilege.READ))
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private UserDetailsService service;

    @Before
    public void initMocks() {
        when(repository.getByUsername(any(String.class))).thenReturn(Optional.of(STUB_USER));
        when(repository.update(any(User.class))).thenReturn(STUB_USER);
        when(service.loadUserByUsername(any(String.class))).thenReturn(STUB_USER);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyGetUser_IfAuthorized() throws Exception {
        mockMvc.perform(get("/users/get/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailOnGetUser_IfUnauthorized() throws Exception {
        mockMvc.perform(get("/users/get/test"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyPutUser_IfAuthorized() throws Exception {
        mockMvc.perform(
                put("/users/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(STUB_USER))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailOnPutUser_IfUnauthorized() throws Exception {
        mockMvc.perform(
                put("/users/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(STUB_USER))
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyDeleteUser_IfAuthorized() throws Exception {
        mockMvc.perform(
                delete("/users/delete")
                        .with(csrf().asHeader())
                        .characterEncoding("utf-8")
                        .requestAttr("username", "user")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailOnDeleteUser_IfUnauthorized() throws Exception {
        mockMvc.perform(
                delete("/users/delete")
                        .with(csrf().asHeader())
                        .requestAttr("username", "user")
        )
                .andExpect(status().is4xxClientError());
    }

}
