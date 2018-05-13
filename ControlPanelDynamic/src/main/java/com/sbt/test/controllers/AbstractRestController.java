package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class AbstractRestController {

    static<T> ResponseEntity<T> ok(T body){
        return ResponseEntity.status(200).body(body);
    }

    static<T> ResponseEntity<T> ok(){
        return ResponseEntity.status(200).build();
    }

    static<T> ResponseEntity<T> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    protected ResponseEntity<User> process(Supplier<ResponseEntity<User>> responseSupplier){
        try {
            return responseSupplier.get();
        } catch (RuntimeException e) {
            log.error("Something really bad happened on getUser operation:", e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

}
