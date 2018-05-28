package com.sbt.test.services;

import com.sbt.test.repository.UserNotExistException;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PersistenceException;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class AbstractUserService {

    private static final String SERVICE_LAYER_EXCEPTION_PREFIX = "Service layer has caught an exception";

    <T> T supplyUserOrThrow(Supplier<T> userSupplier, Function<? super PersistenceException, ? extends UserServiceException> exceptionSupplier) {
        T entity = null;
        try {
            entity = userSupplier.get();
        } catch (UserServiceException serviceEx) {
            // user supplier can throw service exceptions,
            // for example if some preconditions were violated.
            // Service should carefully rethrow them.
            log.info("Service exception thrown: " + serviceEx.getMessage());
            throw serviceEx;
        } catch (UserNotExistException notExist) {
            log.info(SERVICE_LAYER_EXCEPTION_PREFIX + " - user not exist: " + notExist.getMessage());
            throw new UserNotFoundException(notExist);
        } catch (PersistenceException pex) {
            log.info(SERVICE_LAYER_EXCEPTION_PREFIX + " - persistence exception caught: " + pex.getMessage());
            throw exceptionSupplier.apply(pex);
        } catch (RuntimeException rex) {
            log.info(SERVICE_LAYER_EXCEPTION_PREFIX + " - runtime exception caught: " + rex.getMessage());
            throw new UserServiceException(rex);
        }
        if (entity == null) {
            throw new UserNotFoundException(SERVICE_LAYER_EXCEPTION_PREFIX + ": fails to find any entities");
        }
        return entity;
    }

    <T> T supplyUser(Supplier<T> userSupplier) {
        return supplyUserOrThrow(userSupplier, UserServiceException::new);
    }


}
