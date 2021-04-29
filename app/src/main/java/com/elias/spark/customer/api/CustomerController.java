package com.elias.spark.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import SparkCustomerApi.App;
import spark.Request;
import spark.Response;
import spark.Route;

public class CustomerController {

	public static String PATH = "/customer/";
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Route getAll = (Request request, Response response) -> {

		return objectMapper.writeValueAsString(App.customerRepository.getAllBooks());
	};
}
