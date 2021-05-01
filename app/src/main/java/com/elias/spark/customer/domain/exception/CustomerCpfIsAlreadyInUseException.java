package com.elias.spark.customer.domain.exception;

public class CustomerCpfIsAlreadyInUseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerCpfIsAlreadyInUseException() {
		super("The cpf already belongs to a customer.");
	}
}
