package com.sbt.test.controllers;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.UserService;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerLogicTest {

    private static final String MESSAGE = "It's test exception message. Don't panic!!";

    private static User MOCK_USER;
    private UserService service = mock(UserService.class);
    private UserController controller = new UserController(service);

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
    }

    // tests on get
    @Test
    public void returnsSuccessOnGet_ifServiceReturnsClient() {
        when(service.get(any(String.class))).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(service).get(any(String.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnGet_ifServiceThrowsRuntimeException() {
        when(service.get(any(String.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(service).get(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnGet_ifServiceThrowsUserServiceException() {
        when(service.get(any(String.class))).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(service).get(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnGet_ifServiceFoundNothing() {
        // emulate "found nothing" case
        when(service.get(any(String.class))).thenThrow(new UserNotFoundException(MESSAGE));
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(service).get(any(String.class));
        assertEquals("Status is invalid", HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    // tests on add
    @Test
    public void returnsSuccessOnAdd_ifSucceed() {
        when(service.add(any(User.class))).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.add(MOCK_USER);
        verify(service).add(any(User.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnAdd_ifAddThrowsUserServiceException() {
        when(service.add(any(User.class))).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.add(MOCK_USER);
        verify(service).add(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnAdd_ifAddThrowsRuntimeException() {
        when(service.add(any(User.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.add(MOCK_USER);
        verify(service).add(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    // tests on update
    @Test
    public void returnsSuccessOnUpdate_ifSucceed() {
        when(service.update(any(User.class))).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.update(MOCK_USER);
        verify(service).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("User has changed", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnUpdate_ifUpdateThrowsUserServiceException() {
        when(service.update(any(User.class))).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.update(MOCK_USER);
        verify(service).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnAdd_ifUpdateThrowsRuntimeException() {
        when(service.update(any(User.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.update(MOCK_USER);
        verify(service).update(any(User.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    // tests on delete
    @Test
    public void returnsSuccessOnDelete_ifDeleteByUsernameSucceed() {
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(service).delete(any(String.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnDelete_ifDeleteByUsernameThrowsRuntimeException() {
        when(service.delete(any(String.class))).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(service).delete(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnDelete_ifDeleteByUsernameThrowsUserServiceException() {
        when(service.delete(any(String.class))).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(service).delete(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

}
