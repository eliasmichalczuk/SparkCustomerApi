package com.elias.spark.customer.repository;

import java.util.List;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.elias.spark.customer.domain.model.Address;

public interface IAddressRepository {
	@SqlUpdate("insert into address (state, city, neighborhood, zipCode, street, number, additionalInformation, customerId, main)"
	        + " values (:state, :city, :neighborhood, :zipCode, :street, :number, :additionalInformation, :customerId, :main)")
	@GetGeneratedKeys("id")
	Integer insertAddress(@BindBean Address address);

	@SqlQuery("SELECT * from address where id = :id")
	Address getAddressById(@Bind("id") Integer id);

	@SqlQuery("SELECT * from address where customerId = :id")
	List<Address> findAllByCustomerId(@Bind("id") Integer id);

	@SqlUpdate("UPDATE address SET"
	        + " state = :state, city = :city, neighborhood = :neighborhood, zipCode = :zipCode, street = :street, "
	        + "number = :number, additionalInformation = :additionalInformation, main = :main " + " where id = :id")
	void updateAddress(@BindBean Address address);
}
