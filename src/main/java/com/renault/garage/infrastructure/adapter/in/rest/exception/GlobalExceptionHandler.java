package com.renault.garage.infrastructure.adapter.in.rest.exception;

import com.renault.garage.domain.exception.GarageNotFoundException;
import com.renault.garage.domain.exception.VehicleNotFoundException;
import com.renault.garage.domain.exception.VehicleQuotaExceededException;
import com.renault.garage.domain.exception.BusinessException;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GarageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGarageNotFoundException(
            GarageNotFoundException ex, WebRequest request) {
        
        log.error("Garage not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVehicleNotFoundException(
            VehicleNotFoundException ex, WebRequest request) {
        
        log.error("Vehicle not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehicleQuotaExceededException.class)
    public ResponseEntity<ErrorResponse> handleVehicleQuotaExceededException(
            VehicleQuotaExceededException ex, WebRequest request) {
        
        log.error("Vehicle quota exceeded: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, WebRequest request) {
        
        log.error("Business exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(ex.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        log.error("Validation error: {}", ex.getMessage());
        
        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> ErrorResponse.ValidationError.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .build())
            .collect(Collectors.toList());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message("Validation failed")
            .path(request.getDescription(false).replace("uri=", ""))
            .validationErrors(validationErrors)
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error: ", ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message("An unexpected error occurred")
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
