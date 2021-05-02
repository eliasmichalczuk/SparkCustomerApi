package com.elias.spark.customer.repository;

import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.elias.spark.customer.domain.model.Address;

public interface IAddressRepository {
	@SqlUpdate("insert into address (state, city, neighborhood, zipCode, street, number, additionalInformation, customerId, main)"
	        + " values (:state, :city, :neighborhood, :zipCode, :street, :number, :additionalInformation, :customerId, :main)")
	@GetGeneratedKeys("id")
	Integer insertAddress(@BindBean Address address);
}
