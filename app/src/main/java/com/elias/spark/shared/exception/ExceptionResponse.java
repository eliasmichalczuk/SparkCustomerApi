package com.elias.spark.shared.exception;

public class ExceptionResponse {

	public final String name;
	public final String message;

	public ExceptionResponse(String message, String name) {
		super();
		this.message = message;
		this.name = name;
	}

}
