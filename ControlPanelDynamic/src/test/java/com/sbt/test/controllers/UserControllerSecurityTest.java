package com.sbt.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
    private UserService service;

    @MockBean
    private UserDetailsService userDetailsService;

    @Before
    public void initMocks() {
        when(service.get(anyString())).thenReturn(STUB_USER);
        when(service.add(any(User.class))).thenReturn(STUB_USER);
        when(service.update(any(User.class))).thenReturn(STUB_USER);
        when(service.delete(anyString())).thenReturn(STUB_USER);
        when(service.setRoles(anyString(), anyCollection())).thenReturn(STUB_USER);
        when(service.setPrivileges(anyString(), anyCollection())).thenReturn(STUB_USER);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyGetUser_IfAuthorized() throws Exception {
        mockMvc.perform(get("/users/get/test"))
                .andExpect(status().isOk());
        verify(service).get(anyString());
    }

    @Test
    public void shouldFailOnGetUser_IfUnauthorized() throws Exception {
        mockMvc.perform(get("/users/get/test"))
                .andExpect(status().isUnauthorized());
        verify(service, never()).get(anyString());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyAddUser_IfAuthorized() throws Exception {
        mockMvc.perform(
                put("/users/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(STUB_USER))
        )
                .andExpect(status().isOk());
        verify(service).add(any(User.class));
    }

    @Test
    public void shouldFailOnAddUser_IfUnauthorized() throws Exception {
        mockMvc.perform(
                put("/users/add")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(STUB_USER))
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).add(any(User.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyUpdateUser_IfAuthorized() throws Exception {
        mockMvc.perform(
                post("/users/update")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(STUB_USER))
        )
                .andExpect(status().isOk());
        verify(service).update(any(User.class));
    }

    @Test
    public void shouldFailOnUpdateUser_IfUnauthorized() throws Exception {
        mockMvc.perform(
                post("/users/update")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(STUB_USER))
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).update(any(User.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSuccessfullyDeleteUser_IfAuthorized() throws Exception {
        mockMvc.perform(
                delete("/users/delete/test")
                        .with(csrf().asHeader())
                        .characterEncoding("utf-8")
                        .requestAttr("username", "user")
        )
                .andExpect(status().isOk());
        verify(service).delete(any(String.class));
    }

    @Test
    public void shouldFailOnDeleteUser_IfUnauthorized() throws Exception {
        mockMvc.perform(
                delete("/users/delete/test")
                        .with(csrf().asHeader())
                        .requestAttr("username", "user")
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).update(any(User.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldSuccessfullySetRoles_IfAuthorized() throws Exception {
        mockMvc.perform(
                post("/users/setRoles")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(new NameWithAuthorities<Role>(STUB_USER.getUsername(),
                                Sets.newSet(Role.ADMIN))))
        )
                .andExpect(status().isOk());
        verify(service).setRoles(anyString(), anyCollection());
    }

    @Test
    public void shouldFailOnSetRoles_IfUnauthorized() throws Exception {
        mockMvc.perform(
                post("/users/setRoles")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(new NameWithAuthorities<Role>(STUB_USER.getUsername(),
                                Sets.newSet(Role.ADMIN))))
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).setRoles(anyString(), anyCollection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldSuccessfullySetPrivileges_IfAuthorized() throws Exception {
        mockMvc.perform(
                post("/users/setPrivileges")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(new NameWithAuthorities<Privilege>(STUB_USER.getUsername(),
                                Sets.newSet(Privilege.WRITE))))
        )
                .andExpect(status().isOk());
        verify(service).setPrivileges(anyString(), anyCollection());
    }

    @Test
    public void shouldFailOnSetPrivileges_IfUnauthorized() throws Exception {
        mockMvc.perform(
                post("/users/setPrivileges")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new ObjectMapper().writeValueAsString(new NameWithAuthorities<Privilege>(STUB_USER.getUsername(),
                                Sets.newSet(Privilege.WRITE))))
        )
                .andExpect(status().isUnauthorized());
        verify(service, never()).setPrivileges(anyString(), anyCollection());
    }

}
