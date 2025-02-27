package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        CommonResponse<Object> response = CommonResponse.failure(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<CommonResponse<Object>> handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        log.error("Data already exists: {}", ex.getMessage());
        CommonResponse<Object> response = CommonResponse.failure(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleGeneralException(Exception ex) {
        log.error("An error occurred: {}", ex.getMessage());
        CommonResponse<Object> response = CommonResponse.failure("Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
