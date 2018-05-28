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

public class UserServiceTest {

    private static final String MESSAGE = "wow it's definitely not ok!";
    private static final String ENCODED_PASS = "encoded_pass";

    private UserRepository repo = mock(UserRepository.class);
    private PasswordEncoder dummyEncoder = mock(PasswordEncoder.class);
    private final UserService service = new UserService(repo, dummyEncoder);

    private User MOCK_USER = User.builder()
            .username("user")
            .password("test pass")
            .roles(Collections.singleton(Role.USER))
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

    // tests on get
    @Test
    public void getsUser_ifGetUserSucceed() {
        when(repo.get(anyString())).thenReturn(MOCK_USER);
        User user = service.get(MOCK_USER.getUsername());
        assertEquals("User is not equal", MOCK_USER, user);
        verify(repo).get(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void throwsServiceExceptionOnGet_ifSuccessfullyGetsNoUser() {
        when(repo.get(anyString())).thenReturn(null);
        service.get(MOCK_USER.getUsername());
        verify(repo).get(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnGet_ifRepoThrowsPersistanceException() {
        when(repo.get(anyString()))
                .thenThrow(new DataIntegrityViolationException(MESSAGE));
        service.get(MOCK_USER.getUsername());
        verify(repo).get(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnGet_ifRepoThrowsRuntimeException() {
        when(repo.get(anyString()))
                .thenThrow(new RuntimeException(MESSAGE));
        service.get(MOCK_USER.getUsername());
        verify(repo).get(anyString());
    }

    // tests on add
    @Test
    public void addsUser_ifAddSucceed() {
        when(repo.add(any(User.class))).thenReturn(MOCK_USER);
        User user = service.add(MOCK_USER);
        assertEquals("User is not equal", MOCK_USER, user);
        verify(repo).add(any(User.class));
        verify(dummyEncoder).encode(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnAdd_ifRepoThrowsPersistanceException() {
        when(repo.add(any(User.class)))
                .thenThrow(new DataIntegrityViolationException(MESSAGE));
        service.add(MOCK_USER);
        verify(repo).add(any(User.class));
        verify(dummyEncoder).encode(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnAdd_ifRepoThrowsRuntimeException() {
        when(repo.add(any(User.class)))
                .thenThrow(new RuntimeException(MESSAGE));
        service.add(MOCK_USER);
        verify(repo).add(any(User.class));
        verify(dummyEncoder).encode(anyString());
    }

    // tests on update
    @Test
    public void updatesUser_ifUpdateSucceed() {
        when(repo.update(any(User.class))).thenReturn(MOCK_USER);
        User user = service.update(MOCK_USER);
        assertEquals("User is not equal", MOCK_USER, user);
        verify(repo).update(any(User.class));
        verify(dummyEncoder, never()).encode(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnUpdate_ifRepoThrowsPersistanceException() {
        when(repo.update(any(User.class)))
                .thenThrow(new DataIntegrityViolationException(MESSAGE));
        service.update(MOCK_USER);
        verify(repo).update(any(User.class));
        verify(dummyEncoder, never()).encode(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnUpdate_ifRepoThrowsRuntimeException() {
        when(repo.update(any(User.class)))
                .thenThrow(new RuntimeException(MESSAGE));
        service.update(MOCK_USER);
        verify(repo).update(any(User.class));
        verify(dummyEncoder, never()).encode(anyString());
    }

    // tests on delete
    @Test
    public void deletesUser_ifDeleteSucceed() {
        when(repo.delete(anyString())).thenReturn(MOCK_USER);
        service.delete(MOCK_USER.getUsername());
        verify(repo).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnDelete_ifRepoThrowsPersistanceExceptionOnGet() {
        when(repo.get(anyString())).thenThrow(new DataIntegrityViolationException(MESSAGE));
        service.delete(MOCK_USER.getUsername());
        verify(repo).get(anyString());
        verify(repo, never()).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnDelete_ifRepoThrowsRuntimeExceptionOnGet() {
        when(repo.get(anyString())).thenThrow(new DataIntegrityViolationException(MESSAGE));
        service.delete(MOCK_USER.getUsername());
        verify(repo).get(anyString());
        verify(repo, never()).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnDelete_ifRepoThrowsPersistanceExceptionOnDelete() {
        when(repo.get(anyString())).thenReturn(MOCK_USER);
        doThrow(new DataIntegrityViolationException(MESSAGE))
                .when(repo).delete(anyString());
        service.delete(MOCK_USER.getUsername());
        verify(repo).get(anyString());
        verify(repo).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnDelete_ifRepoThrowsRuntimeExceptionOnDelete() {
        when(repo.get(anyString())).thenReturn(MOCK_USER);
        doThrow(new RuntimeException(MESSAGE))
                .when(repo).delete(anyString());
        service.delete(MOCK_USER.getUsername());
        verify(repo).get(anyString());
        verify(repo).delete(anyString());
    }

}
