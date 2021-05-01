package com.elias.spark.customer.application.cmd;

import java.util.UUID;

import com.elias.spark.customer.domain.Customer;

public class CreateCustomerCmd {
	private String name;

	public String getName() {
		return name;
	}

	public CreateCustomerCmd setName(String name) {
		this.name = name;
		return this;
	}

	public Customer toCustomer() {
		return new Customer(1l, UUID.randomUUID(), name);
	}

}
