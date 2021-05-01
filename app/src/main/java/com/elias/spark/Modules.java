package com.elias.spark;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public final class Modules {

//	static class CustomerModule extends AbstractModule {
//		@Provides
//		@Singleton
//		private ObjectMapper provideObjectMapper() {
//			return new ObjectMapper();
//		}
//
//		@Provides
//		private CustomerRepository provideCustomerRepository() {
//			return new CustomerRepository();
//		}
//	}

	static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
		}

		@Singleton
		@Provides
		static Jdbi provideDataSource() {
			Jdbi jdbi = Jdbi.create("jdbc:mysql://127.0.0.1:3306/sparkcustomerdb", "root", "root");
			return jdbi;
		}

		@Singleton
		@Provides
		private static Flyway provideFlyway()
		        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			var flyway = Flyway.configure()
			                   .dataSource("jdbc:mysql://127.0.0.1:3306/sparkcustomerdb", "root", "root")
			                   .load();
			return flyway;
		}

	}
}