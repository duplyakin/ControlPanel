package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerLogicTest {

    private static final String MESSAGE = "wow it's definitely not ok!";

    private static User MOCK_USER = User.builder()
            .username("test user")
            .password("test pass")
            .id(123L)
            .credentialsNonExpired(true)
            .accountNonExpired(true)
            .enabled(true)
            .accountNonLocked(true)
            .build();

    private UserRepository repo = mock(UserRepository.class);

    private UserController controller = new UserController(repo);

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
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
        assertNull("User has changed", response.getBody());
    }

    @Test
    public void returnsFailOnGet_ifRepositoryFoundNothing() {
        when(repo.getByUsername(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<User> response = controller.get(MOCK_USER.getUsername());
        verify(repo).getByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull("User is not empty", response.getBody());
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
        assertNull("User has changed", response.getBody());
    }

    @Test
    public void returnsSuccessOnDelete_ifDeleteByUsernameSucceed() {
//        when(repo.deleteByUsername(any(String.class))).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(repo).deleteByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertNull("User has changed", response.getBody());
    }

    @Test
    public void returnsFailOnDelete_ifDeleteByUsernameThrowsException() {
        doThrow(new RuntimeException(MESSAGE)).when(repo).deleteByUsername(any(String.class));
        ResponseEntity<User> response = controller.delete(MOCK_USER.getUsername());
        verify(repo).deleteByUsername(any(String.class));
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("User has changed", response.getBody());
    }

}
