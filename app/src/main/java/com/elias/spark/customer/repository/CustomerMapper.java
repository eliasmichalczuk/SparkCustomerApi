package com.elias.spark.customer.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.flywaydb.core.internal.jdbc.RowMapper;

import com.elias.spark.customer.domain.Customer;
import com.elias.spark.customer.domain.Gender;

class CustomerMapper implements RowMapper<Customer> {
	@Override
	public Customer mapRow(ResultSet rs) throws SQLException {
		return new Customer(rs.getInt("id"),
		                    UUID.fromString(rs.getString("uuid")),
		                    rs.getString("name"),
		                    rs.getDate("birthDate").toLocalDate(),
		                    rs.getString("cpf"),
		                    Gender.valueOf(rs.getString("gender")),
		                    rs.getString("email"),
		                    null);
	}
}