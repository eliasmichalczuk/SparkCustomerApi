package com.elias.spark.customer.api;

import com.elias.spark.customer.application.cmd.CreateCustomerCmd;
import com.elias.spark.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import spark.Request;
import spark.Response;
import spark.Route;

public class CustomerController {

	public static String PATH = "/customer/";
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Inject
	private static CustomerRepository customerRepository;

	public static Route getAll = (Request request, Response response) -> {

//		return objectMapper.writeValueAsString(App.customerRepository.getAllBooks());
		return null;
	};

	public static Route save = (Request request, Response response) -> {
//		Customer customer = objectMapper.reader(Customer.class).readValue(request.bodyAsBytes());
//		return objectMapper.writeValueAsString(App.customerRepository.getAllBooks());
		String name = request.queryParams("name");
		var cmd = new CreateCustomerCmd().setName(name);

		return customerRepository.save(cmd.toCustomer());
	};
}
