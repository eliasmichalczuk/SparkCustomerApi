package com.elias.spark.shared.db;

import org.jdbi.v3.core.Jdbi;

public class DbConnection {

	public static Jdbi get() {

		Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost:5432/sparkCustomerDb", "dev", "dev");
		return jdbi;
	}
}
