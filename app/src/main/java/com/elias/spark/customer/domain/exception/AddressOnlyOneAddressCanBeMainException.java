package com.elias.spark.customer.domain.exception;

public class AddressOnlyOneAddressCanBeMainException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AddressOnlyOneAddressCanBeMainException() {
		super("There can be only one main address for each customer.");
	}
}
