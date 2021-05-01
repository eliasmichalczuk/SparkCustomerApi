package com.elias.spark.customer.application.cmd;

import java.time.LocalDate;

import com.elias.spark.customer.domain.Gender;

public class CreateCustomerCmd {
	private String name;
	private LocalDate birthDate;
	private String cpf;
	private Gender gender;

//	public CreateCustomerCmd(String name, LocalDate birthDate, String cpf, Gender gender) {
//		super();
//		this.name = name;
//		this.birthDate = birthDate;
//		this.cpf = cpf;
//		this.gender = gender;
//	}
//
//	public Customer toCustomer() {
//		return new Customer(1l, UUID.randomUUID(), name, birthDate, cpf, gender);
//	}

}
