package com.elias.spark.customer.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public Customer update(Customer customer) {
		jdbi.useHandle(handle -> handle.createUpdate("UPDATE customer SET name = :name, birthDate = :birthDate, email = :email, gender = :gender, cpf = :cpf")
		                               .bind("name", customer.getName())
		                               .bind("birthDate", customer.getBirthDate())
		                               .bind("email", customer.getEmail())
		                               .bind("gender", customer.getGender())
		                               .bind("cpf", customer.getCpf())
		                               .execute());
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, customer.getId())
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

	public List<Customer> findAll(String name, String birthDate) {
		Map<String, String> params = new HashMap<>();
		params.put("name", name);
		params.put("birthDate", birthDate);
		return (List<Customer>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer"
		        + " where 1=1 and (:name is not null and name = :name) "
		        + " and (:birthDate is not null and birthDate = :birthDate) and 1=1 ")
		                                                        .bindMap(params)
		                                                        .mapTo(Customer.class)
		                                                        .list());
	}

	public void delete(Long id) {
		jdbi.withHandle(handle -> handle.execute("DELETE from customer where id = ?", id));
	}
}
