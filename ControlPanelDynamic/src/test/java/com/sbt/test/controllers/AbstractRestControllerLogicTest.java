package com.sbt.test.controllers;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractRestControllerLogicTest {

    private static final String MESSAGE = "It's test exception message. Don't panic!!";

    private AbstractRestController controller = new AbstractRestController();
    @SuppressWarnings("unchecked")
    private Supplier<User> userSupplier = mock(Supplier.class);

    private User MOCK_USER = User.builder()
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

    @Test
    public void returnsOkWithUser_ifSuccessfullySupplies() {
        when(userSupplier.get()).thenReturn(MOCK_USER);
        ResponseEntity<User> entity = controller.process(userSupplier);
        assertEquals("Response is not ok", HttpStatus.OK, entity.getStatusCode());
        assertEquals("User is invalid!", MOCK_USER, entity.getBody());
    }

    @Test
    public void returnsNotFound_ifSupplierThrowsUserNotFoundException() {
        when(userSupplier.get()).thenThrow(new UserNotFoundException(MESSAGE));
        ResponseEntity<User> entity = controller.process(userSupplier);
        assertEquals("Response is not 404", HttpStatus.NOT_FOUND, entity.getStatusCode());
        assertNull("Body is not empty!", entity.getBody());
    }

    @Test
    public void returnsPreconditionFailed_ifSupplierThrowsUserServiceException() {
        when(userSupplier.get()).thenThrow(new UserServiceException(MESSAGE));
        ResponseEntity<User> entity = controller.process(userSupplier);
        assertEquals("Response is not 412", HttpStatus.PRECONDITION_FAILED, entity.getStatusCode());
        assertNull("Body is not empty!", entity.getBody());
    }

    @Test
    public void returnsPreconditionFailed_ifSupplierThrowsRuntimeException() {
        when(userSupplier.get()).thenThrow(new RuntimeException(MESSAGE));
        ResponseEntity<User> entity = controller.process(userSupplier);
        assertEquals("Response is not 412", HttpStatus.PRECONDITION_FAILED, entity.getStatusCode());
        assertNull("Body is not empty!", entity.getBody());
    }

}
