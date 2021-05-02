package com.elias.spark.customer.api;

import static com.elias.spark.shared.exception.ExceptionHandler.wrap;
import static spark.Spark.get;

import java.util.List;

import com.elias.spark.customer.application.CustomerApplicationService;
import com.elias.spark.customer.domain.Address;
import com.elias.spark.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import spark.Request;
import spark.Response;

@Singleton
public class AddressController {

	public static String PATH = "/customers/:customerId/address";
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final CustomerRepository customerRepository;
	private final CustomerApplicationService customerApplicationService;

	@Inject
	public AddressController(CustomerRepository customerRepository,
	                         CustomerApplicationService customerApplicationService) {
		super();
		this.customerRepository = customerRepository;
		this.customerApplicationService = customerApplicationService;
		objectMapper.findAndRegisterModules();
	}

	public void start() {
		get(PATH, wrap(this::getAll));
//		get(PATH + "/:id", wrap(this::findById));
//		post(PATH, "application/json", wrap(this::save));
//		put(PATH + "/:id", "application/json", wrap(this::update));
//		delete(PATH + "/:id", wrap(this::deleteOne));
	}

	public List<Address> getAll(Request request, Response response) {
		var customerId = Integer.parseInt(request.params("customerId"));
		return customerRepository.findAllByCustomerId(customerId);
	};

}
