package ru.flendger.demo.store.demo.store.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler
    ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error(exception.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception) {
        log.error(exception.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    ResponseEntity<?> handleCartEmptyException(CartEmptyException exception) {
        log.error(exception.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
