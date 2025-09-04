package com.example.uade.tpo.ecommerce_grupo10.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, Object> body = Map.of(
                "message", "Credenciales inválidas",
                "status", "unauthorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body); // 401
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = Map.of(
                "message", ex.getMessage(),
                "status", "bad_request");
        return ResponseEntity.badRequest().body(body); // 400
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Datos inválidos");
        body.put("status", "validation_error");
        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body); // 400
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> body = Map.of(
                "message", "Error interno del servidor",
                "status", "error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body); // 500
    }
}
