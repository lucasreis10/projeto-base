package com.example.base.infra.api.controllers;

import com.example.base.application.usuario.excption.UsuarioOuSenhaIncorretosException;
import com.example.base.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UsuarioOuSenhaIncorretosException.class)
    public ResponseEntity<?> handleDomainException(final UsuarioOuSenhaIncorretosException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(exception));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(final DomainException exception) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(exception));
    }

}

class ApiError {
    private String message;


    private ApiError(String message) {
        this.message = message;
    }

    static ApiError from(final RuntimeException ex) {
        return new ApiError(ex.getMessage());
    }

    public String getMessage() {
        return message;
    }

}