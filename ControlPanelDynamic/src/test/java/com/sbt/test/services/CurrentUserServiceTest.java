package com.sbt.test.services;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CurrentUserServiceTest {
    private static final String MESSAGE = "wow it's definitely not ok!";
    private static final String ENCODED_PASS = "encoded_pass";
    private PasswordEncoder dummyEncoder = mock(PasswordEncoder.class);
    private UserRepository repo = mock(UserRepository.class);
    private final CurrentUserService service = new CurrentUserService(repo, dummyEncoder);
    private User MOCK_USER = User.builder()
            .username("user")
            .password("test pass")
            .springRoles(Collections.singleton(Role.USER))
            .privileges(Collections.singleton(Privilege.READ))
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();

    @Before
    public void init() {
        when(dummyEncoder.encode(anyString())).thenReturn(ENCODED_PASS);
    }

    // tests on getCurrentUser
    @Test
    public void getsCurrentUser_ifGetUserSucceed() {
        when(repo.get(anyString())).thenReturn(MOCK_USER);
        User user = service.getCurrentUser(MOCK_USER.getUsername());
        assertEquals("User is not equal", MOCK_USER, user);
        verify(repo).get(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void throwsServiceExceptionOnGetCurrentUser_ifSuccessfullyGetsNoUser() {
        when(repo.get(anyString())).thenReturn(null);
        service.getCurrentUser(MOCK_USER.getUsername());
        verify(repo).get(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnGetCurrentUser_ifRepoThrowsPersistanceException() {
        when(repo.get(anyString()))
                .thenThrow(new DataIntegrityViolationException(MESSAGE));
        service.getCurrentUser(MOCK_USER.getUsername());
        verify(repo).get(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnGetCurrentUser_ifRepoThrowsRuntimeException() {
        when(repo.get(anyString()))
                .thenThrow(new RuntimeException(MESSAGE));
        service.getCurrentUser(MOCK_USER.getUsername());
        verify(repo).get(anyString());
    }

}
