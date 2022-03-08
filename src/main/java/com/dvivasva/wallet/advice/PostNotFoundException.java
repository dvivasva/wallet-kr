package com.dvivasva.wallet.advice;


import com.dvivasva.wallet.utils.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String id) {
        super("Element with param: " + id +" is not found in database.");
    }

    @RestControllerAdvice
    @Slf4j
    static
    class RestExceptionHandler {

        @ExceptionHandler(PostNotFoundException.class)
        ResponseEntity<?> postNotFound(PostNotFoundException ex) {
            log.info("handling exception::" + ex);
            return new ResponseEntity<>(new MessageResponse(false,"Not found in database" ) ,HttpStatus.NOT_FOUND);
        }

    }

}


