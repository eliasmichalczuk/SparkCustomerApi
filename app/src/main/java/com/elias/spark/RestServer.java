package com.elias.spark;

import com.elias.spark.customer.api.CustomerController;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RestServer {

	private final CustomerController customerController;

	@Inject
	public RestServer(CustomerController customerController) {
		super();
		this.customerController = customerController;
	}

	public void start() {
		customerController.start();
	}

}
