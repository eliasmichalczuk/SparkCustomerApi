package com.elias.spark.customer.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.elias.spark.customer.domain.Address;
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

	public Customer saveFullCustomer(Customer customer) {
		var saved = this.save(customer);
		customer.getAddresses().forEach(a -> a.setCustomerId(saved.getId()));
		customer.setAddresses(this.insertAddress(customer.getAddresses()));
		return saved;
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
		                                         .mapTo(Integer.class)
		                                         .one());
		return (Customer) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                                  .bind(0, id)
		                                                  .mapTo(Customer.class)
		                                                  .one());
	}

	public Integer insertAddress(Address address) {
		return jdbi.withHandle(handle -> handle.createUpdate("insert into address (state, city, neighborhood, zipCode, street, number, additionalInformation, customerId, main)"
		        + " values (:state, :city, :neighborhood, :zipCode, :street, :number, :additionalInformation, :customerId, :main)")
		                                       .bind("state", address.getState())
		                                       .bind("city", address.getCity())
		                                       .bind("neighborhood", address.getNeighborhood())
		                                       .bind("zipCode", address.getZipCode())
		                                       .bind("street", address.getStreet())
		                                       .bind("number", address.getNumber())
		                                       .bind("additionalInformation", address.getAdditionalInformation())
		                                       .bind("customerId", address.getCustomerId())
		                                       .bind("main", address.getMain())
		                                       .executeAndReturnGeneratedKeys("id")
		                                       .mapTo(Integer.class)
		                                       .one());
	}

	public List<Address> insertAddress(List<Address> addresses) {
		var ids = new ArrayList<Integer>();
		addresses.forEach(adr -> ids.add(this.insertAddress(adr)));
		return findAllById(ids);
	}

	public List<Address> findAllById(List<Integer> ids) {
		return (List<Address>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from addres where id IN (?)")
		                                                       .bind(0, ids)
		                                                       .mapTo(Address.class)
		                                                       .list());
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

	public Optional<Customer> findById(Integer id) {
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
		return (List<Customer>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer")
//		        + " where 1=1 and (:name is not null and name = :name) "
//		        + " and (:birthDate is not null and birthDate = :birthDate) and 1=1 ")
//		                                                        .bindMap(params)
		                                                        .mapTo(Customer.class)
		                                                        .list());
	}

	public void delete(Long id) {
		jdbi.withHandle(handle -> handle.execute("DELETE from customer where id = ?", id));
	}

	@Singleton
	public interface IAddressRepository extends SqlObject {
		@SqlUpdate("insert into address (state, city, neighborhood, zipCode, street, number, additionalInformation, customerId, main)"
		        + " values (:state, :city, :neighborhood, :zipCode, :street, :number, :additionalInformation, :customerId, :main)")
		void insert(@BindBean Address contact);
	}
}
