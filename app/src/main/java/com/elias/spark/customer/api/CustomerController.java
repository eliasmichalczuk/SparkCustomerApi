package com.elias.spark.customer.api;

import static com.elias.spark.shared.exception.ExceptionHandler.wrap;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.IOException;
import java.util.List;

import com.elias.spark.customer.api.dto.CustomerCmdDto;
import com.elias.spark.customer.application.CustomerApplicationService;
import com.elias.spark.customer.domain.Customer;
import com.elias.spark.customer.repository.CustomerRepository;
import com.elias.spark.shared.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonGenerationException;
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
		get(PATH, wrap(this::getAll));
		get(PATH + "/:id", wrap(this::findById));
		post(PATH, "application/json", wrap(this::save));
		put(PATH + "/:id", "application/json", wrap(this::update));
		delete(PATH + "/:id", wrap(this::deleteOne));
	}

	public List<Customer> getAll(Request request, Response response) {
		var name = request.queryParams("name");
		var birthDate = request.queryParams("birthDate");
		return customerRepository.findAll(name, birthDate);
	};

	public Object findById(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException {
		var customer = customerRepository.findByIdManualJoin(Integer.parseInt(request.params("id")));
		response.status(200);
		return customer;
	};

	public Customer save(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException {
		var dto = objectMapper.readValue(request.body(), CustomerCmdDto.class);
		var customer = customerApplicationService.save(dto.toCreateCmd());
		response.status(201);
		return customer;
	};

	public Customer update(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException, NotFoundException {
		var dto = objectMapper.readValue(request.body(), CustomerCmdDto.class);

		var customer = customerApplicationService.update(dto.toUpdateCmd(Integer.valueOf(request.params("id"))));
		response.status(200);
		return customer;
	};

	public String deleteOne(Request request, Response response)
	        throws JsonGenerationException, JsonMappingException, IOException, NotFoundException {
		customerRepository.delete(Long.valueOf(request.params("id")));
		response.status(200);
		return null;
	};
}
