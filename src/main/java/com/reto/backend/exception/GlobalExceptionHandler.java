package com.reto.backend.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new HashMap<>();
        
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST,"VALIDATION_ERROR", fieldErrors);        

        return ResponseEntity.badRequest().body(response);        
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException exception) {

        Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST,"BUSINESS_ERROR", exception.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException exception) {
        
        if (exception.getCause() instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {

            String allowedValues = Arrays.stream(ife.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            String message = String.format(
                    "Invalid value '%s' for enum %s. Allowed values are: %s",
                    ife.getValue(),
                    ife.getTargetType().getSimpleName(),
                    allowedValues
            );

            Map<String, Object> response = createResponse(
                    HttpStatus.BAD_REQUEST,
                    "JSON_PARSE_ERROR",
                    message
            );

            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST,
                "JSON_PARSE_ERROR",
                "Invalid JSON format"
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(ResourceNotFoundException exception) {

        Map<String, Object> response = createResponse(HttpStatus.NOT_FOUND,"RESOURCE_NOT_FOUND", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {

        Map<String, Object> response = createResponse(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL_SERVER_ERROR", exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }

    private Map<String, Object> createResponse(HttpStatus status, String error, Object details) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", error);
        response.put("details", details);

        return response;
    }
}
