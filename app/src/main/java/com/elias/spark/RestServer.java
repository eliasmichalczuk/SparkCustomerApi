package com.elias.spark;

import com.elias.spark.customer.api.AddressController;
import com.elias.spark.customer.api.CustomerController;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RestServer {

	private final CustomerController customerController;
	private final AddressController addressController;

	@Inject
	public RestServer(CustomerController customerController, AddressController addressController) {
		super();
		this.customerController = customerController;
		this.addressController = addressController;
	}

	public void start() {
		customerController.start();
		addressController.start();
	}

}
