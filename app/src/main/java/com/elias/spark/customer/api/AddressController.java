package com.elias.spark.customer.api;

import static com.elias.spark.shared.exception.ExceptionHandler.wrap;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import com.elias.spark.customer.api.dto.AddressCmdDto;
import com.elias.spark.customer.application.CustomerApplicationService;
import com.elias.spark.customer.domain.model.Address;
import com.elias.spark.customer.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		get(PATH + "/:id", wrap(this::findById));
		post(PATH, "application/json", wrap(this::save));
		put(PATH + "/:id", "application/json", wrap(this::update));
		delete(PATH + "/:id", wrap(this::deleteOne));
	}

	public List<Address> getAll(Request request, Response response) {
		var customerId = Integer.parseInt(request.params("customerId"));
		return customerRepository.findAllByCustomerId(customerId);
	};

	public Address save(Request request, Response response) throws JsonMappingException, JsonProcessingException {
		var customerId = Integer.parseInt(request.params("customerId"));
		var dto = objectMapper.readValue(request.body(), AddressCmdDto.class);
		response.status(201);
		return customerApplicationService.saveAddress(dto.toAddress(), customerId);
	};

	public Address findById(Request request, Response response) {
		var id = Integer.parseInt(request.params("id"));
		return customerRepository.getAddressById(id);
	};

	public Address update(Request request, Response response) throws JsonMappingException, JsonProcessingException {
		var customerId = Integer.parseInt(request.params("customerId"));
		var id = Integer.parseInt(request.params("id"));
		var dto = objectMapper.readValue(request.body(), AddressCmdDto.class);
		response.status(201);
		return customerApplicationService.update(dto.toAddress(id), customerId);
	};

	public Void deleteOne(Request request, Response response) {
		var id = Integer.parseInt(request.params("id"));
		customerRepository.deleteAddress(id);
		return null;
	};
}
