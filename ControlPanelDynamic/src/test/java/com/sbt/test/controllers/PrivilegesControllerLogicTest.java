package com.sbt.test.controllers;

import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.AuthorityService;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PrivilegesControllerLogicTest {

    private static final String MESSAGE = "It's test exception message. Don't panic!!";
    private static User MOCK_USER;
    private static NameWithAuthorities<Privilege> NAME_WITH_PRIVILEGES;
    private AuthorityService service = mock(AuthorityService.class);
    private PrivilegesController controller = new PrivilegesController(service);

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
        NAME_WITH_PRIVILEGES = new NameWithAuthorities<>(MOCK_USER.getUsername(),
                Collections.singleton(Privilege.WRITE));
    }


    // tests on setPrivileges
    @Test
    public void returnsSuccessOnSetPrivileges_ifSuccessfullySetsNewPrivileges() {
        when(service.setPrivileges(anyString(), anyCollection())).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(service).setPrivileges(anyString(), anyCollection());
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("Privileges are wrong", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnSetPrivileges_ifSetPrivilegesThrowsRuntimeException() {
        when(service.setPrivileges(anyString(), anyCollection())).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(service).setPrivileges(anyString(), anyCollection());
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnSetPrivileges_ifSetPrivilegesThrowsUserServiceException() {
        when(service.setPrivileges(anyString(), anyCollection())).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.setPrivileges(NAME_WITH_PRIVILEGES);
        verify(service).setPrivileges(anyString(), anyCollection());
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

}
