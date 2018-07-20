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

public class RolesControllerLogicTest {

    private static final String MESSAGE = "It's test exception message. Don't panic!!";
    private static User MOCK_USER;
    private static NameWithAuthorities<Role> NAME_WITH_ROLES;
    private AuthorityService service = mock(AuthorityService.class);
    private RolesController controller = new RolesController(service);

    private static User createMockUser() {
        return User.builder()
                .username("test user")
                .password("test pass")
                .id(123L)
                .privileges(Collections.singleton(Privilege.READ))
                .springRoles(Collections.singleton(Role.USER))
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
    }


    // tests on setRoles
    @Test
    public void returnsSuccessOnSetRoles_ifSuccessfullySetsNewRoles() {
        when(service.setRoles(anyString(), anyCollection())).thenReturn(MOCK_USER);
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(service).setRoles(anyString(), anyCollection());
        assertEquals("Status is invalid", HttpStatus.OK, response.getStatusCode());
        assertEquals("Roles are wrong", MOCK_USER, response.getBody());
    }

    @Test
    public void returnsFailOnSetRoles_ifSetRolesThrowsRuntimeException() {
        when(service.setRoles(anyString(), anyCollection())).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(service).setRoles(anyString(), anyCollection());
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

    @Test
    public void returnsFailOnSetRoles_ifSetRolesThrowsUserServiceException() {
        when(service.setRoles(anyString(), anyCollection())).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> response = controller.setRoles(NAME_WITH_ROLES);
        verify(service).setRoles(anyString(), anyCollection());
        assertEquals("Status is invalid", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertNull("Body is not empty", response.getBody());
    }

}
