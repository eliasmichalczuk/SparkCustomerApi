package com.elias.spark.customer.domain.exception;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(Integer id) {
		super("Customer with id " + id + " not found.");
	}
}
