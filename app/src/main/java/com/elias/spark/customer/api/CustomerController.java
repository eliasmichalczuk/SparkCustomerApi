package com.elias.spark.customer.api;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;

import com.elias.spark.customer.api.dto.CreateCustomerCmdDto;
import com.elias.spark.customer.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import spark.Request;
import spark.Response;
import spark.Route;

@Singleton
public class CustomerController {

	public static String PATH = "/customers";
	private final ObjectMapper objectMapper = new ObjectMapper();
	private CustomerRepository customerRepository;

	@Inject
	public CustomerController(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.findAndRegisterModules();
	}

	public void start() {
		get(PATH, this::getAll);
		get(PATH + "/:id", this::findById);
		post(PATH, "application/json", this::save);
	}

	public Route getAll(Request request, Response response) {
		return null;
	};

	public String findById(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException {
		var customer = customerRepository.findById(Long.parseLong(request.params("id")));
		return objectMapper.writeValueAsString(customer);
	};

	public String save(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException {
		var dto = objectMapper.readValue(request.body(), CreateCustomerCmdDto.class);

		var customer = customerRepository.save(dto.toCustomer());
		return objectMapper.writeValueAsString(customer);
	};
}
