package com.hexaware.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<?> bookNotFound(BookNotFoundException e){
		ErrorDetails error = new ErrorDetails(LocalDateTime.now(),e.getMessage(),"Book Exception",HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BookAlreadyExistsException.class)
	public ResponseEntity<?> bookNotFound(BookAlreadyExistsException e){
		ErrorDetails error = new ErrorDetails(LocalDateTime.now(),e.getMessage(),"Book Conflict",HttpStatus.NO_CONTENT.value());
		return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> validationException(ValidationException e){
		ErrorDetails error = new ErrorDetails(LocalDateTime.now(),e.getMessage(),"Validation Exception",HttpStatus.NOT_IMPLEMENTED.value());
		return new ResponseEntity<>(error,HttpStatus.NOT_IMPLEMENTED);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationException(MethodArgumentNotValidException e){
    	
    	Map<String, String> errors = new HashMap<>();
        
        List<ObjectError> errList = e.getBindingResult().getAllErrors();
        errList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

    	
    	ErrorDetails error = new ErrorDetails(LocalDateTime.now(),errors,"Validation Failed",HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}