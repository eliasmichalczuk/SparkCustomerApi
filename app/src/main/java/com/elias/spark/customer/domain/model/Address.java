package com.elias.spark.customer.domain.model;

public class Address {
	private Integer id;
	private String state;
	private String city;
	private String neighborhood;
	private String zipCode;
	private String street;
	private String number;
	private String additionalInformation;
	private Integer customerId;
	private boolean main;

	public Address(Integer id,
	               String state,
	               String city,
	               String neighborhood,
	               String zipCode,
	               String street,
	               String number,
	               String additionalInformation,
	               Integer customerId,
	               boolean main) {
		super();
		this.id = id;
		this.state = state;
		this.city = city;
		this.neighborhood = neighborhood;
		this.zipCode = zipCode;
		this.street = street;
		this.number = number;
		this.additionalInformation = additionalInformation;
		this.customerId = customerId;
		this.main = main;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public boolean isMain() {
		return main;
	}
}
