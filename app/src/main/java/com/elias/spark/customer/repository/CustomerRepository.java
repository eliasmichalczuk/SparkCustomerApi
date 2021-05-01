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
		jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO customer(id, uuid, name) VALUES (?, ?, ?)")
		                               .bind(0, customer.getId())
		                               .bind(1, UUID.randomUUID().toString())
		                               .bind(2, customer.getName())
		                               .execute());
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, customer.getId())
		                                                  .mapTo(Customer.class)
		                                                  .one());
//		                                                  .mapToBean(Customer.class));
	}

	public Customer findById() {
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, 1)
		                                                  .mapTo(Customer.class)
		                                                  .one());
	}
}
