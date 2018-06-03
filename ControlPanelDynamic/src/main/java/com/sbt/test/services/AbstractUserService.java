package com.sbt.test.services;

import com.sbt.test.repository.exceptions.UserNotExistException;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PersistenceException;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class AbstractUserService {

    private static final String SERVICE_LAYER_EXCEPTION_PREFIX = "Exception caught on service layer - ";

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
            log.info(SERVICE_LAYER_EXCEPTION_PREFIX + "thrown by persistence layer: user not exist: " + notExist.getMessage());
            throw new UserNotFoundException(notExist);
        } catch (PersistenceException pex) {
            log.info(SERVICE_LAYER_EXCEPTION_PREFIX + "thrown by persistence layer:", pex);
            throw exceptionSupplier.apply(pex);
        } catch (RuntimeException rex) {
            log.error(SERVICE_LAYER_EXCEPTION_PREFIX + "a runtime exception, you should have expected this: ", rex);
            throw new UserServiceException(rex);
        } catch (Exception e) {
            log.error(SERVICE_LAYER_EXCEPTION_PREFIX + "an exception, you should have expected this: ", e);
            throw new UserServiceException(e);
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
