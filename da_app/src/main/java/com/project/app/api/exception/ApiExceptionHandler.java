package com.project.app.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler  {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);
	@ExceptionHandler({MethodArgumentNotValidException.class})
	protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
		List<ApiSubError> subErrors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			Object rejectedValue = ((FieldError) error).getRejectedValue();
			String message = error.getDefaultMessage();
			subErrors.add(new ApiSubError(fieldName, rejectedValue, message));
		});
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", subErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ApiSubError subError = new ApiSubError(null, null, ex.getMessage());
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", Collections.singletonList(subError));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
	}

	@ExceptionHandler({RuntimeException.class}) // Thêm xử lý cho RuntimeException
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex) {
		log.error("An unexpected runtime error occurred: {}", ex.getMessage(), ex);
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
	}

	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
		log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,  ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
	}
}
