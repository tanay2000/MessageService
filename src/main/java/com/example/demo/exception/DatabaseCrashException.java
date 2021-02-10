package com.example.demo.exception;

public class DatabaseCrashException extends RuntimeException{
    public DatabaseCrashException(String message) {
        super(message);
    }
}
