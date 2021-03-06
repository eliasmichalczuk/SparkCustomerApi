package com.elias.spark.customer.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.elias.spark.customer.domain.enums.Gender;
import com.elias.spark.customer.domain.exception.CustomerMaxAgeExceededException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class Customer {

	private Integer id;
	private UUID uuid;
	private String name;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate birthDate;
	private String cpf;
	private Gender gender;
	private String email;
	private List<Address> addresses;

	public Customer() {
		super();
	}

	public Customer(Integer id,
	                UUID uuid,
	                String name,
	                LocalDate birthDate,
	                String cpf,
	                Gender gender,
	                String email,
	                List<Address> addresses) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.gender = gender;
		this.email = email;
		this.addresses = addresses;

		verifyAge();
	}

	public void update(String name, LocalDate birthDate, String cpf, Gender gender, String email) {
		this.name = name;
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.gender = gender;
		this.email = email;
		verifyAge();
	}

	private void verifyAge() {
		if (ageIsOverAHundredYearsOld()) {
			throw new CustomerMaxAgeExceededException();
		}
	}

	private boolean ageIsOverAHundredYearsOld() {
		return (LocalDate.now().getYear() - birthDate.getYear()) > 100;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addAddress(Address address) {
		this.addresses.add(address);
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
