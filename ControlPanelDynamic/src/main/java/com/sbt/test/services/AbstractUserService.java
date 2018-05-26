package com.sbt.test.services;

import com.sbt.test.entities.User;
import com.sbt.test.repository.UserNotExistException;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PersistenceException;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class AbstractUserService {

    private final PasswordEncoder encoder;

    private String NO_ENTITY_MESSAGE = "No users had been found";

    public AbstractUserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    String encode(String s) {
        return encoder.encode(s);
    }

    User encodeUser(User user) {
        return User.builder()
                .id(user.getId())
                .privileges(user.getPrivileges())
                .roles(user.getRoles())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .username(user.getUsername())
                .password(encode(user.getPassword()))
                .build();
    }

    <T> T supplyUserOrThrow(Supplier<T> userSupplier, Function<? super PersistenceException, ? extends UserServiceException> exceptionSupplier) {
        T entity = null;
        try {
            entity = userSupplier.get();
        } catch (UserServiceException serviceEx) {
            // user supplier can throw service exceptions,
            // for example if some preconditions were violated.
            // Service should carefully rethrow them.
            log.info("Service exception: " + serviceEx.getMessage());
            throw serviceEx;
        } catch (UserNotExistException notExist) {
            log.info("Persistence exception: " + notExist.getMessage());
            throw new UserNotFoundException(notExist);
        } catch (PersistenceException pex) {
            log.info("Persistence exception: " + pex.getMessage());
            throw exceptionSupplier.apply(pex);
        } catch (RuntimeException rex) {
            log.info("Runtime exception: " + rex.getMessage());
            throw new UserServiceException(rex);
        }
        if (entity == null) {
            throw new UserNotFoundException(NO_ENTITY_MESSAGE);
        }
        return entity;
    }

    <T> T supplyUser(Supplier<T> userSupplier) {
        return supplyUserOrThrow(userSupplier, UserServiceException::new);
    }


}
