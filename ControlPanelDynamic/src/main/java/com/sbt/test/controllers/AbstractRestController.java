package com.sbt.test.controllers;

import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.UserServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@Slf4j
public class AbstractRestController {

    private static final String CONTROLLER_LAYER_EXCEPTION_PREFIX = "Controller layer exception - ";

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
            log.info(CONTROLLER_LAYER_EXCEPTION_PREFIX + "user was not found: ", e.getMessage());
            return notFound();
        } catch (UserServiceException e) {
            log.info(CONTROLLER_LAYER_EXCEPTION_PREFIX + "exception on service layer: ", e.getMessage());
            return preconditionFailed();
        } catch (RuntimeException e) {
            log.info(CONTROLLER_LAYER_EXCEPTION_PREFIX + "caught runtime exception: ", e.getMessage());
            return preconditionFailed();
        }
    }

}
