package com.sbt.test.controllers;

import com.sbt.test.dto.OldAndNewPass;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.CurrentUserService;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CurrentUserControllerLogicTest {

    private static final String MESSAGE = "wow it's definitely not ok!";
    private static final String USERNAME = "test user";
    private static final String OLD_PASS = "old_pass";
    private static final String NEW_PASS = "new_pass";
    private static final Principal PRINCIPAL = mock(Principal.class);
    private static User MOCK_USER;
    private static OldAndNewPass OLD_AND_NEW_PASS = OldAndNewPass.builder()
            .newPassword(NEW_PASS)
            .oldPassword(OLD_PASS)
            .build();
    private CurrentUserService service = mock(CurrentUserService.class);
    private CurrentUserController controller = new CurrentUserController(service);

    private static User createMockUser() {
        return User.builder()
                .username(USERNAME)
                .password(OLD_PASS)
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
        when(PRINCIPAL.getName()).thenReturn(USERNAME);
    }

    // tests on getCurrentUser
    @Test
    public void returnsSuccessOnGetCurrentUser_ifServiceReturnsUser() {
        when(service.getCurrentUser(anyString())).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.getCurrentUser(PRINCIPAL);
        verify(service).getCurrentUser(USERNAME);
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnOnGetCurrentUser_ifServiceFoundNothing() {
        when(service.getCurrentUser(anyString())).thenThrow(new UserNotFoundException(MESSAGE));
        ResponseEntity<User> response = controller.getCurrentUser(PRINCIPAL);
        verify(service).getCurrentUser(USERNAME);
        assertEquals("Status is invalid", HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnGetCurrentUser_ifServiceThrowsRuntimeException() {
        when(service.getCurrentUser(any(String.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.getCurrentUser(PRINCIPAL);
        verify(service).getCurrentUser(anyString());
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnGetCurrentUser_ifServiceThrowsUserServiceException() {
        when(service.getCurrentUser(anyString())).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.getCurrentUser(PRINCIPAL);
        verify(service).getCurrentUser(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    // tests on changePassword
    @Test
    public void returnsSuccessOnChangePassword_ifServiceSucceed() {
        when(service.changePassword(anyString(), anyString(), anyString())).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.changePassword(PRINCIPAL, OLD_AND_NEW_PASS);
        verify(service).changePassword(USERNAME, OLD_PASS, NEW_PASS);
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnOnChangePassword_ifServiceThrowsUserServiceException() {
        when(service.changePassword(anyString(), anyString(), anyString())).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.changePassword(PRINCIPAL, OLD_AND_NEW_PASS);
        verify(service).changePassword(USERNAME, OLD_PASS, NEW_PASS);
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnOnChangePassword_ifServiceThrowsRuntimeException() {
        when(service.changePassword(anyString(), anyString(), anyString())).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.changePassword(PRINCIPAL, OLD_AND_NEW_PASS);
        verify(service).changePassword(USERNAME, OLD_PASS, NEW_PASS);
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

}
