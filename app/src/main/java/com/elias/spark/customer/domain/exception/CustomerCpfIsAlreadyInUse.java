package com.elias.spark.customer.domain.exception;

public class CustomerCpfIsAlreadyInUse extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerCpfIsAlreadyInUse() {
		super("The cpf already belongs to a customer.");
	}
}
