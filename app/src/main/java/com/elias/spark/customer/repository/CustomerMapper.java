package com.elias.spark.customer.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.flywaydb.core.internal.jdbc.RowMapper;

import com.elias.spark.customer.domain.Customer;

class CustomerMapper implements RowMapper<Customer> {
	@Override
	public Customer mapRow(ResultSet rs) throws SQLException {
		return new Customer(rs.getLong("id"), UUID.fromString(rs.getString("uuid")), rs.getString("name"));
	}
}