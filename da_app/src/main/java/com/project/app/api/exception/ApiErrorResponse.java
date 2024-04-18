package com.project.app.api.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class ApiErrorResponse {
	private  HttpStatus status;
	private  String message;
	private  List<ApiSubError> errors;

	public ApiErrorResponse(HttpStatus status, String message, List<ApiSubError> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
}
