package com.ramos.helpdesk.resources.exceptios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

}
