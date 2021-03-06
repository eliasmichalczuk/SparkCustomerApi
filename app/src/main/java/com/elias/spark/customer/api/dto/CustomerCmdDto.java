package com.elias.spark.customer.api.dto;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.elias.spark.customer.application.cmd.CreateCustomerCmd;
import com.elias.spark.customer.application.cmd.UpdateCustomerCmd;
import com.elias.spark.customer.domain.enums.Gender;

public class CustomerCmdDto {

	private String name;
	private String email;
	private LocalDate birthDate;
	private String cpf;
	private Gender gender;
	private List<AddressCmdDto> addresses;

	public CreateCustomerCmd toCreateCmd() {
		return new CreateCustomerCmd(UUID.randomUUID(),
		                             name,
		                             birthDate,
		                             cpf,
		                             gender,
		                             email,
		                             addresses.stream().map(AddressCmdDto::toAddress).collect(toList()));
	}

	public UpdateCustomerCmd toUpdateCmd(Integer id) {
		return new UpdateCustomerCmd(id,
		                             UUID.randomUUID(),
		                             name,
		                             birthDate,
		                             cpf,
		                             gender,
		                             email,
		                             addresses.stream().map(AddressCmdDto::toAddress).collect(toList()));
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

	public List<AddressCmdDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressCmdDto> addresses) {
		this.addresses = addresses;
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
