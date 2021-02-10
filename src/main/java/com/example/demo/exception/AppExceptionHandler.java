package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.jws.WebResult;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(DatabaseCrashException.class)
    public ResponseEntity<Object> handleDatabaseException(DatabaseCrashException ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage("DATABASE_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage("INVALID_REQUEST", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage("UNKNOWN_EXCEPTION", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
