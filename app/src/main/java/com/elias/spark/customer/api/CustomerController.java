package com.elias.spark.customer.api;

import static java.util.stream.Collectors.toList;
import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import java.util.List;

import com.elias.spark.customer.api.dto.CreateCustomerCmdDto;
import com.elias.spark.customer.application.CustomerApplicationService;
import com.elias.spark.customer.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import spark.Request;
import spark.Response;

@Singleton
public class CustomerController {

	public static String PATH = "/customers";
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final CustomerRepository customerRepository;
	private final CustomerApplicationService customerApplicationService;

	@Inject
	public CustomerController(CustomerRepository customerRepository,
	                          CustomerApplicationService customerApplicationService) {
		super();
		this.customerRepository = customerRepository;
		this.customerApplicationService = customerApplicationService;
		objectMapper.findAndRegisterModules();
	}

	public void start() {
		get(PATH, this::getAll);
		get(PATH + "/:id", this::findById);
		post(PATH, "application/json", this::save);
	}

	public List<String> getAll(Request request, Response response) {
		return customerRepository.findAll().stream().map(t -> {
			try {
				return objectMapper.writeValueAsString(t);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "";
			}
		}).collect(toList());
	};

	public String findById(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException {
		var customer = customerRepository.findById(Long.parseLong(request.params("id")));
		if (customer.isPresent()) {
			response.status(200);
			return objectMapper.writeValueAsString(customer);
		}
		response.status(404);
		return "Customer not found.";
	};

	public String save(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException {
		var dto = objectMapper.readValue(request.body(), CreateCustomerCmdDto.class);

		try {
			var customer = customerApplicationService.save(dto.toCmd());
			response.status(201);
			return objectMapper.writeValueAsString(customer);
		} catch (RuntimeException e) {
			response.status(400);
			return e.getMessage();
		}
	};
}
