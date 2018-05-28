package com.sbt.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.AuthorityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivilegesController.class)
@RunWith(SpringRunner.class)
public class PrivilegesControllerSecurityTest {

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
    private AuthorityService service;

    @Before
    public void initMocks() {
        when(service.setRoles(anyString(), anyCollection())).thenReturn(STUB_USER);
        when(service.setPrivileges(anyString(), anyCollection())).thenReturn(STUB_USER);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldSuccessfullySetPrivileges_IfAuthorized() throws Exception {
        mockMvc.perform(
                post("/privileges/set")
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
                post("/privileges/set")
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
