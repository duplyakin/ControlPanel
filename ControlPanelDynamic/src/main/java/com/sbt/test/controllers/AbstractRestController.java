package com.sbt.test.controllers;

import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

/**
 * Abstract layer for controllers.
 * Catches exceptions and handles them, wraps result into ResponseEntity.
 */
@Slf4j
public class AbstractRestController {

    private static final String CONTROLLER_LAYER_EXCEPTION_PREFIX = "Exception caught on controller layer - ";

    static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.status(200).body(body);
    }

    static <T> ResponseEntity<T> ok() {
        return ResponseEntity.status(200).build();
    }

    static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    static <T> ResponseEntity<T> preconditionFailed() {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    }

    <T> ResponseEntity<T> process(Supplier<T> responseSupplier) {
        try {
            return ok(responseSupplier.get());
        } catch (UserNotFoundException e) {
            log.info(CONTROLLER_LAYER_EXCEPTION_PREFIX + "thrown by service layer: user was not found: ", e.getMessage());
            return notFound();
        } catch (UserServiceException e) {
            log.info(CONTROLLER_LAYER_EXCEPTION_PREFIX + "thrown by service layer: ", e.getMessage());
            return preconditionFailed();
        } catch (RuntimeException e) {
            log.info(CONTROLLER_LAYER_EXCEPTION_PREFIX + "a runtime exception, you should have expected this: ", e);
            return preconditionFailed();
        } catch (Exception e) {
        log.error(CONTROLLER_LAYER_EXCEPTION_PREFIX + "an exception, you should have expected this: ", e);
        return preconditionFailed();
    }
    }

}
