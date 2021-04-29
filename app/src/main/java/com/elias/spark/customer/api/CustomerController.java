package com.elias.spark.customer.api;

import SparkCustomerApi.App;
import spark.Request;
import spark.Response;
import spark.Route;

public class CustomerController {

	public static String PATH = "/customer/";

	public static Route getAll = (Request request, Response response) -> {
	    return App.customerRepository.getAllBooks();
	};
}
