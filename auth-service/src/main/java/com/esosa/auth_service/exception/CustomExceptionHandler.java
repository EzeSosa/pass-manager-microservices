package com.esosa.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleNoSuchElementException(NoSuchElementException exception) {
        return new ExceptionMessage(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ResponseBody
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleResponseStatusException(ResponseStatusException exception) {
        return new ExceptionMessage(
                exception.getReason(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ExceptionMessage(
                exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessage handleBadCredentialsException(BadCredentialsException exception) {
        return new ExceptionMessage(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionMessage handleAccessDeniedException(AccessDeniedException exception) {
        return new ExceptionMessage(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.value()
        );
    }
}