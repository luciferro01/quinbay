package com.mohil_bansal.assignment.student_learning_management_system.exception;

public class DataAlreadyExistsException extends RuntimeException{

    public  DataAlreadyExistsException(String message) {
        super(message);
    }
}