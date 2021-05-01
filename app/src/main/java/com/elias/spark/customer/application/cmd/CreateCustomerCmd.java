package com.elias.spark.customer.application.cmd;

import java.time.LocalDate;
import java.util.UUID;

import com.elias.spark.customer.domain.Customer;
import com.elias.spark.customer.domain.Gender;

public class CreateCustomerCmd {
	private final String name;
	private final LocalDate birthDate;
	private final String cpf;
	private final Gender gender;
	private final String email;

	public CreateCustomerCmd(UUID uuid, String name, LocalDate birthDate, String cpf, Gender gender, String email) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.gender = gender;
		this.email = email;
	}

	public Customer toCustomer() {
		return new Customer(1l, UUID.randomUUID(), name, birthDate, cpf, gender, email);
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

}
