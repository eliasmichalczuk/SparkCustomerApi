package com.elias.spark.customer.application.cmd;

import java.time.LocalDate;
import java.util.UUID;

import com.elias.spark.customer.domain.Customer;
import com.elias.spark.customer.domain.Gender;

public class UpdateCustomerCmd {
	private Long id;
	private final String name;
	private final LocalDate birthDate;
	private final String cpf;
	private final Gender gender;
	private final String email;

	public UpdateCustomerCmd(Long id,
	                         UUID uuid,
	                         String name,
	                         LocalDate birthDate,
	                         String cpf,
	                         Gender gender,
	                         String email) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.gender = gender;
		this.email = email;
		this.id = id;
	}

	public Customer toCustomer() {
		return new Customer(id, UUID.randomUUID(), name, birthDate, cpf, gender, email);
	}

	public UpdateCustomerCmd setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
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
