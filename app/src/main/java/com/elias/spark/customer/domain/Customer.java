package com.elias.spark.customer.domain;

import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Customer {

	private Long id;

	@JsonSerialize(using = UUIDSerializer.class)
	@JsonDeserialize(using = UUIDDeserializer.class)
	private UUID uuid;
	private String name;

	public Customer(Long id, UUID uuid, String name) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

}
