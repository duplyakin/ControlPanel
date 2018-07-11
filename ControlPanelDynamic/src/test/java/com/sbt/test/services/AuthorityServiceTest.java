package com.sbt.test.services;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthorityServiceTest {
    private static final String MESSAGE = "wow it's definitely not ok!";

    private UserRepository repo = mock(UserRepository.class);
    private final AuthorityService service = new AuthorityService(repo);

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


    // tests on setRoles
    @Test
    public void setsRoles_ifSetRolesSucceed() {
        when(repo.get(anyString())).thenReturn(MOCK_USER);
        when(repo.update(any(User.class))).thenReturn(MOCK_USER);
        service.setRoles(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo).update(any(User.class));
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetRoles_ifRepoThrowsPersistanceExceptionOnGet() {
        doThrow(new DataIntegrityViolationException(MESSAGE))
                .when(repo).get(anyString());
        service.setRoles(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo, never()).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetRoles_ifRepoThrowsRuntimeExceptionOnGet() {
        doThrow(new RuntimeException(MESSAGE))
                .when(repo).get(anyString());
        service.setRoles(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo, never()).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetRoles_ifRepoThrowsPersistanceExceptionOnDelete() {
        doThrow(new DataIntegrityViolationException(MESSAGE))
                .when(repo).delete(anyString());
        service.setRoles(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetRoles_ifRepoThrowsRuntimeExceptionOnDelete() {
        doThrow(new RuntimeException(MESSAGE))
                .when(repo).delete(anyString());
        service.setRoles(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo).delete(anyString());
    }

    // tests on setPrivileges
    @Test
    public void setsPrivileges_ifSetPrivilegesSucceed() {
        when(repo.get(anyString())).thenReturn(MOCK_USER);
        when(repo.update(any(User.class))).thenReturn(MOCK_USER);
        service.setPrivileges(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo).update(any(User.class));
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetPrivileges_ifRepoThrowsPersistanceExceptionOnGet() {
        doThrow(new DataIntegrityViolationException(MESSAGE))
                .when(repo).get(anyString());
        service.setPrivileges(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo, never()).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetPrivileges_ifRepoThrowsRuntimeExceptionOnGet() {
        doThrow(new RuntimeException(MESSAGE))
                .when(repo).get(anyString());
        service.setPrivileges(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo, never()).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetPrivileges_ifRepoThrowsPersistanceExceptionOnDelete() {
        doThrow(new DataIntegrityViolationException(MESSAGE))
                .when(repo).delete(anyString());
        service.setPrivileges(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo).delete(anyString());
    }

    @Test(expected = UserServiceException.class)
    public void throwsServiceExceptionOnSetPrivileges_ifRepoThrowsRuntimeExceptionOnDelete() {
        doThrow(new RuntimeException(MESSAGE))
                .when(repo).delete(anyString());
        service.setPrivileges(MOCK_USER.getUsername(), Collections.emptySet());
        verify(repo).get(anyString());
        verify(repo).delete(anyString());
    }
}
