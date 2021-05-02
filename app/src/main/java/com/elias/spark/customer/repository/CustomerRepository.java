package com.elias.spark.customer.repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import com.elias.spark.customer.domain.exception.AddressNotFoundException;
import com.elias.spark.customer.domain.exception.CustomerNotFoundException;
import com.elias.spark.customer.domain.model.Address;
import com.elias.spark.customer.domain.model.Customer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomerRepository implements IAddressRepository {

	private final Jdbi jdbi;
	private final IAddressRepository addressRepository;

	@Inject
	public CustomerRepository(Jdbi jdbi) {
		this.jdbi = jdbi;
		this.addressRepository = jdbi.onDemand(IAddressRepository.class);
		jdbi.registerRowMapper(Customer.class, (rs, ctx) -> new CustomerMapper().mapRow(rs));
		jdbi.registerRowMapper(Address.class, (rs, ctx) -> new AddressMapper().mapRow(rs));
	}

	public Customer saveCustomerWithAddresses(Customer customer) {
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

	public void updateAddress(Address address) {
		addressRepository.updateAddress(address);
	}

	public Customer findByIdManualJoin(Integer id) {
		var customer = findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
		var addresses = findAllByCustomerId(id);
		customer.setAddresses(addresses);
		return customer;
	}

	// not working
	public Optional<Customer> customerWithAddressesById(int id) {
		return (Optional<Customer>) jdbi.withHandle(handle -> handle.createQuery("SELECT c.id c_id, c.name c_name, c.uuid c_uuid, c.birthDate c_birthDate,"
		        + " c.cpf c_cpf, c.gender c_gender, c.email c_email, addr.id addr_id" + " from customer c "
		        + " LEFT JOIN address addr on c.id = addr.customerId where c.id = :id")
		                                                            .bind("id", id)
		                                                            .registerRowMapper(ConstructorMapper.factory(Customer.class,
		                                                                                                         "c_"))
		                                                            .registerRowMapper(ConstructorMapper.factory(Address.class,
		                                                                                                         "addr_"))
		                                                            .reduceRows(new LinkedHashMap<Integer, Customer>(),
		                                                                        (map, rowView) -> {
			                                                                        Customer customer = map.computeIfAbsent(rowView.getColumn("c_id",
			                                                                                                                                  Integer.class),
			                                                                                                                customerId -> rowView.getRow(Customer.class));

			                                                                        if (rowView.getColumn("p_id",
			                                                                                              Long.class) != null) {
				                                                                        customer.addAddress(rowView.getRow(Address.class));
			                                                                        }

			                                                                        return map;
		                                                                        })
		                                                            .values()
		                                                            .stream()
		                                                            .findFirst());
	}

	public List<Address> insertAddress(List<Address> addresses) {
		addresses.forEach(adr -> this.insertAddress(adr));
		return findAllByCustomerId(addresses.iterator().next().getCustomerId());
	}

	public List<Address> findAllById(List<Integer> ids) {
		return (List<Address>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from address where id IN (:ids)")
		                                                       .bind("ids", ids.toArray())
		                                                       .mapTo(Address.class)
		                                                       .list());
	}

	public List<Address> findAllByCustomerId(Integer id) {
//		return (List<Address>) jdbi.withHandle(handle -> handle.createQuery("SELECT * from address where customerId = :id")
//		                                                       .bind("id", id)
//		                                                       .mapTo(Address.class)
//		                                                       .list());
		return addressRepository.findAllByCustomerId(id);
	}

	public Address getAddressById(Integer id) {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * from address where id = :id")
		                                       .bind("id", id)
		                                       .mapTo(Address.class)
		                                       .findOne()
		                                       .orElseThrow(() -> new AddressNotFoundException(id)));
	}

	public Integer insertAddress(Address a) {
		return addressRepository.insertAddress(a);
	}

	public Integer insertAddressManual(Address address) {
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

	public Customer update(Customer customer) {
		jdbi.useHandle(handle -> handle.createUpdate("UPDATE customer SET name = :name, birthDate = :birthDate, email = :email, gender = :gender, cpf = :cpf"
		        + " where id = :id")
		                               .bind("name", customer.getName())
		                               .bind("birthDate", customer.getBirthDate())
		                               .bind("email", customer.getEmail())
		                               .bind("gender", customer.getGender())
		                               .bind("cpf", customer.getCpf())
		                               .bind("id", customer.getId())
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

	public Customer getById(Integer id) {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * from customer where id = ?")
		                                       .bind(0, id)
		                                       .mapTo(Customer.class)
		                                       .findOne())
		           .orElseThrow(() -> new CustomerNotFoundException(id));
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

	public void delete(Integer id) {
		jdbi.withHandle(handle -> handle.execute("DELETE from customer where id = ?", id));
	}

	public void deleteAddress(Integer id) {
		jdbi.withHandle(handle -> handle.execute("DELETE from address where id = ?", id));
	}
}
