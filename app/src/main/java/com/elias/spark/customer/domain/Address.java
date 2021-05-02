package com.elias.spark.customer.domain;

public class Address {
	private String state;
	private String city;
	private String neighborhood;
	private String zipCode;
	private String street;
	private String number;
	private String additionalInformation;
	private boolean main;

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public boolean getMain() {
		return main;
	}

	// Setter Methods

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public void setMain(boolean main) {
		this.main = main;
	}
}
