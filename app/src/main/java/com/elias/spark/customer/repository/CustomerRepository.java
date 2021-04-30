package com.elias.spark.customer.repository;

import java.util.UUID;

import org.jdbi.v3.core.Jdbi;

import com.elias.spark.customer.domain.Customer;
import com.google.inject.Singleton;

@Singleton
public class CustomerRepository {

	public Customer save(Customer customer) {
		Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:5432/sparkCustomerDb", "dev", "dev");
		jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO customer(id, uuid, name) VALUES (?, ?, ?)")
		                               .bind(0, customer.getId())
		                               .bind(1, UUID.randomUUID())
		                               .bind(2, customer.getName())
		                               .execute());
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, customer.getId())
		                                                  .mapToBean(Customer.class));
	}
}
