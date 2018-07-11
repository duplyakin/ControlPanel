package com.sbt.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.test.dto.OldAndNewPass;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.CurrentUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrentUserController.class)
@RunWith(SpringRunner.class)
public class CurrentUserControllerSecurityTest {

    private final String PASS = new BCryptPasswordEncoder().encode("pass");

    private final User STUB_USER = User.builder()
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .username("test user")
            .password(PASS)
            .springRoles(Collections.singleton(Role.USER))
            .privileges(Collections.singleton(Privilege.READ))
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrentUserService service;

    @Before
    public void initMocks() {
        when(service.getCurrentUser(anyString())).thenReturn(STUB_USER);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyGetCurrentUser_IfAuthorized() throws Exception {
        mockMvc.perform(get("/currentUser/get")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
                .andExpect(status().isOk());
        verify(service).getCurrentUser(anyString());
    }

    @Test
    public void shouldFailOnGetCurrentUser_IfUnauthorized() throws Exception {
        mockMvc.perform(get("/currentUser/get")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).getCurrentUser(anyString());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyChangePassword_IfAuthorized() throws Exception {
        mockMvc.perform(post("/currentUser/changePassword")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(new ObjectMapper().writeValueAsString(OldAndNewPass.builder()
                        .oldPassword("pass")
                        .newPassword("pass2")
                        .build()))
        )
                .andExpect(status().isOk());
        verify(service).changePassword(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldFailOnChangePassword_IfUnauthorized() throws Exception {
        mockMvc.perform(post("/currentUser/changePassword")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(new ObjectMapper().writeValueAsString(OldAndNewPass.builder()
                        .oldPassword("pass")
                        .newPassword("pass2")
                        .build()))
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).changePassword(anyString(), anyString(), anyString());
    }


}
