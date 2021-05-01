package com.elias.spark.customer.repository;

import java.util.UUID;

import org.jdbi.v3.core.Jdbi;

import com.elias.spark.customer.domain.Customer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomerRepository {

	private final Jdbi jdbi;

	@Inject
	public CustomerRepository(Jdbi jdbi) {
		super();
		this.jdbi = jdbi;
		jdbi.registerRowMapper(Customer.class, (rs, ctx) -> new CustomerMapper().mapRow(rs));
	}

	public Customer save(Customer customer) {
		var id = jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO customer(uuid, name) VALUES (?, ?)")
		                                         .bind(0, UUID.randomUUID().toString())
		                                         .bind(1, customer.getName())
		                                         .executeAndReturnGeneratedKeys("id")
		                                         .mapTo(Long.class)
		                                         .one());
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, id)
		                                                  .mapTo(Customer.class)
		                                                  .one());
	}

	public Customer findById(Long id) {
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, id)
		                                                  .mapTo(Customer.class)
		                                                  .one());
	}
}
