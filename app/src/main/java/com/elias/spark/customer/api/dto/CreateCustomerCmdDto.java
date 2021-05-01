package com.elias.spark.customer.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.elias.spark.customer.domain.Customer;
import com.elias.spark.customer.domain.Gender;

public class CreateCustomerCmdDto {

	private String name;
	private String email;
	private LocalDate birthDate;
	private String cpf;
	private Gender gender;

	public Customer toCustomer() {
		return new Customer(1l, UUID.randomUUID(), name, birthDate, cpf, gender, email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
