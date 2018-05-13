package com.sbt.test.controllers;

import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerLogicTest {

    private static final String MESSAGE = "wow it's definitely not ok!";

    private static User MOCK_USER;
    private static NameWithAuthorities<Role> NAME_WITH_ROLES;
    private static NameWithAuthorities<Privilege> NAME_WITH_PRIVILEGES;
    private UserRepository repo = mock(UserRepository.class);
    private UserController controller = new UserController(repo);

    private static User createMockUser() {
        return User.builder()
                .username("test user")
                .password("test pass")
                .id(123L)
                .privileges(Collections.singleton(Privilege.READ))
                .roles(Collections.singleton(Role.USER))
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .enabled(true)
                .accountNonLocked(true)
                .build();
    }

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        MOCK_USER = createMockUser();
        NAME_WITH_ROLES = new NameWithAuthorities<>(MOCK_USER.getUsername(),
                Collections.singleton(Role.ADMIN));
        NAME_WITH_PRIVILEGES = new NameWithAuthorities<>(MOCK_USER.getUsername(),
                Collections.singleton(Privilege.WRITE));
    }

    @Test
    public void returnsSuccessOnGet_ifRepositoryReturnsClient() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.of(MOCK_USER));
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnGet_ifRepositoryThrowsException() {
        when(repo.getByUsername(any(String.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnGet_ifRepositoryFoundNothing() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsSuccessOnAdd_ifAddSucceed() {
        when(repo.update(any(User.class))).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.add(MOCK_USER);
        verify(repo).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnAdd_ifAddThrowsException() {
        when(repo.update(any(User.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.add(MOCK_USER);
        verify(repo).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsSuccessOnDelete_ifDeleteByUsernameSucceed() {
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(repo).deleteByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnDelete_ifDeleteByUsernameThrowsException() {
        doThrow(new RuntimeException(MESSAGE)).when(repo).deleteByUsername(any(String.class));
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(repo).deleteByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsNotFoundOnSetRoles_ifCantFindUser() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsSuccessOnSetRoles_ifSuccessfullySetsNewRoles() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.of(MOCK_USER));
        when(repo.update(MOCK_USER)).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(repo).getByUsername(any(String.class));
        verify(repo).update(any(User.class));
        User expectedUser = createMockUser();
        expectedUser.setRoles(NAME_WITH_ROLES.getRoles());
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("Roles are wrong", expectedUser, response.getBody());
    }

    @Test
    public void returnsFailOnSetRoles_ifGetByUsernameThrowsException() {
        when(repo.getByUsername(any(String.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsSuccessOnSetRoles_ifUpdateFails() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.of(MOCK_USER));
        when(repo.update(MOCK_USER)).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(repo).getByUsername(any(String.class));
        verify(repo).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsNotFoundOnSetPrivileges_ifCantFindUser() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsSuccessOnSetPrivileges_ifSuccessfullySetsNewPrivileges() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.of(MOCK_USER));
        when(repo.update(MOCK_USER)).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(repo).getByUsername(any(String.class));
        verify(repo).update(any(User.class));
        User expectedUser = createMockUser();
        expectedUser.setPrivileges(NAME_WITH_PRIVILEGES.getRoles());
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("Privileges are wrong", expectedUser, response.getBody());
    }

    @Test
    public void returnsFailOnSetPrivileges_ifGetByUsernameThrowsException() {
        when(repo.getByUsername(any(String.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsSuccessOnSetPrivileges_ifUpdateFails() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.of(MOCK_USER));
        when(repo.update(MOCK_USER)).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(repo).getByUsername(any(String.class));
        verify(repo).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

}
