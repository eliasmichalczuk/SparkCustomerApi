package com.elias.spark.customer.domain.exception;

public class CustomerMaxAgeExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerMaxAgeExceededException() {
		super("Max customer age allowed is 100 years old.");
	}
}
