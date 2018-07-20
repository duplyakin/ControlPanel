package com.sbt.test.services;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.exceptions.UserNotExistException;
import com.sbt.test.services.exceptions.UserServiceException;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractUserServiceTest {

    private static final String MESSAGE = "It's test exception message. Don't panic!!";

    private AbstractUserService service = new AbstractUserService();

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

    @SuppressWarnings("unchecked")
    private Supplier<User> userSupplier = mock(Supplier.class);

    @SuppressWarnings("unchecked")
    private Function<PersistenceException, UserServiceException> errorTransformer = mock(Function.class);

    // tests on getCurrentUser
    @Test
    public void returnsUser_ifSuccessfullySupplies() {
        when(userSupplier.get()).thenReturn(MOCK_USER);
        User user = service.supplyUserOrThrow(userSupplier, errorTransformer);
        assertEquals("User is invalid!", MOCK_USER, user);
    }

    @Test(expected = UserServiceException.class)
    public void throwsUserServiceException_ifSuppliedNull() {
        when(userSupplier.get()).thenReturn(null);
        service.supplyUserOrThrow(userSupplier, errorTransformer);
    }

    @Test(expected = UserServiceException.class)
    public void throwFurther_ifSupplierThrowsUserServiceException() {
        when(userSupplier.get()).thenThrow(new UserServiceException(MESSAGE));
        service.supplyUserOrThrow(userSupplier, errorTransformer);
    }

    @Test(expected = UserServiceException.class)
    public void throwUserServiceException_ifSupplierThrowsUserNotExistException() {
        when(userSupplier.get()).thenThrow(new UserNotExistException(MESSAGE));
        service.supplyUserOrThrow(userSupplier, errorTransformer);
    }

    @Test(expected = UserServiceException.class)
    public void throwUserServiceException_ifSupplierThrowsPersistenceException() {
        when(userSupplier.get()).thenThrow(new PersistenceException(MESSAGE));
        when(errorTransformer.apply(any(PersistenceException.class))).thenReturn(new UserServiceException(MESSAGE));
        service.supplyUserOrThrow(userSupplier, errorTransformer);
    }

    @Test(expected = UserServiceException.class)
    public void throwUserServiceException_ifSupplierThrowsRuntimeException() {
        when(userSupplier.get()).thenThrow(new RuntimeException(MESSAGE));
        service.supplyUserOrThrow(userSupplier, errorTransformer);
    }

}
