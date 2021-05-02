package com.elias.spark.customer.domain.exception;

public class AddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AddressNotFoundException(Integer id) {
		super("Address with id " + id + " not found.");
	}
}
