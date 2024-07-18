package com.ramos.helpdesk.resources.exceptios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ramos.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.ramos.helpdesk.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandrer extends RuntimeException{

	private static final long serialVersionUID = 1L;

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandarError> objectNotFoundException(
			ObjectNotFoundException ex, HttpServletRequest request){
		
		StandarError error = 
				new StandarError(System.currentTimeMillis(), 
						HttpStatus.NOT_FOUND.value(), 
						"Object not found", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandarError> objectNotFoundException(
			DataIntegrityViolationException ex, HttpServletRequest request){
		
		StandarError error = 
				new StandarError(System.currentTimeMillis(), 
						HttpStatus.BAD_REQUEST.value(), 
						"Vialação de dados", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> objectNotFoundException(
			MethodArgumentNotValidException ex, HttpServletRequest request){
		
		ValidationError error = 
				new ValidationError(System.currentTimeMillis(), 
						HttpStatus.BAD_REQUEST.value(), 
						"Validation Error", "Erro na vialação dos campos", request.getRequestURI());
		for(FieldError x: ex.getBindingResult().getFieldErrors()) {
			error.addErrors(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
