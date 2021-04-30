package com.elias.spark;

import com.elias.spark.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class GuiceModule extends AbstractModule {

//	@Override
//	protected void configure() {
//		bind(App.class).in(Singleton.class);
//	}

	@Provides
	@Singleton
	private ObjectMapper provideObjectMapper() {
		return new ObjectMapper();
	}

	@Provides
	private CustomerRepository provideCustomerRepository() {
		return new CustomerRepository();
	}

//	@Provides
//	private CustomerController provideCustomerController() {
//		return new CustomerController();
//	}
}