package com.example.springbootmybatisdemo.exception;

public class StudentClassNotFoundException extends RuntimeException {

    public StudentClassNotFoundException(String message) {
        super(message);
    }
}
