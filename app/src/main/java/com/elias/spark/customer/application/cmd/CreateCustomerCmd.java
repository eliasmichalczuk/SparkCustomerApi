package com.elias.spark.customer.application.cmd;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.elias.spark.customer.domain.enums.Gender;
import com.elias.spark.customer.domain.model.Address;
import com.elias.spark.customer.domain.model.Customer;

public class CreateCustomerCmd {
	private final String name;
	private final LocalDate birthDate;
	private final String cpf;
	private final Gender gender;
	private final String email;
	private final List<Address> addresses;

	public CreateCustomerCmd(UUID uuid,
	                         String name,
	                         LocalDate birthDate,
	                         String cpf,
	                         Gender gender,
	                         String email,
	                         List<Address> addresses) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.gender = gender;
		this.email = email;
		this.addresses = addresses;
	}

	public Customer toCustomer() {
		return new Customer(1, UUID.randomUUID(), name, birthDate, cpf, gender, email, addresses);
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public Gender getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

}
