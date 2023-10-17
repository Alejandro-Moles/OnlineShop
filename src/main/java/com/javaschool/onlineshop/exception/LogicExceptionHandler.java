package com.javaschool.onlineshop.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LogicExceptionHandler {

    @ExceptionHandler(ResourceDuplicate.class)
    public ResponseEntity<?> handleConflict(ResourceDuplicate e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(NoExistData.class)
    public ResponseEntity<?> handleConflict(NoExistData e){
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
