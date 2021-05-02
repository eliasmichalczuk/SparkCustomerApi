package com.elias.spark.customer.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.flywaydb.core.internal.jdbc.RowMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

import com.elias.spark.customer.domain.Address;

@RegisterBeanMapper(Address.class)
class AddressMapper implements RowMapper<Address> {
	@Override
	public Address mapRow(ResultSet rs) throws SQLException {
		return new Address(rs.getInt("id"),
		                   rs.getString("state"),
		                   rs.getString("city"),
		                   rs.getString("neighborhood"),
		                   rs.getString("zipCode"),
		                   rs.getString("street"),
		                   rs.getString("number"),
		                   rs.getString("additionalInformation"),
		                   rs.getInt("customerId"),
		                   rs.getBoolean("main"));
	}
}