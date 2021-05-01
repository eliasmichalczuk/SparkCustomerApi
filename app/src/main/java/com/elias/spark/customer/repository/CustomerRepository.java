package com.elias.spark.customer.repository;

import java.util.Optional;
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
		var id = jdbi.withHandle(handle -> handle.createUpdate("INSERT INTO customer(uuid, name, birthDate, email, gender, cpf) VALUES (?, ?, ?, ?, ?, ?)")
		                                         .bind(0, UUID.randomUUID().toString())
		                                         .bind(1, customer.getName())
		                                         .bind(2, customer.getBirthDate())
		                                         .bind(3, customer.getEmail())
		                                         .bind(4, customer.getGender())
		                                         .bind(5, customer.getCpf())
		                                         .executeAndReturnGeneratedKeys("id")
		                                         .mapTo(Long.class)
		                                         .one());
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, id)
		                                                  .mapTo(Customer.class)
		                                                  .one());
	}

	public Optional<Customer> findById(Long id) {
		return (Optional<Customer>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                            .bind(0, id)
		                                                            .mapTo(Customer.class)
		                                                            .findOne());
	}

	public Optional<Customer> findByCpf(String cpf) {
		return (Optional<Customer>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where cpf = ?")
		                                                            .bind(0, cpf)
		                                                            .mapTo(Customer.class)
		                                                            .findOne());
	}
}
