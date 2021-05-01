package com.elias.spark.customer.application;

import com.elias.spark.customer.application.cmd.CreateCustomerCmd;
import com.elias.spark.customer.domain.exception.CustomerCpfIsAlreadyInUse;
import com.elias.spark.customer.repository.CustomerRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomerApplicationService {

	public static String PATH = "/customers";
	private CustomerRepository customerRepository;

	@Inject
	public CustomerApplicationService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Object save(CreateCustomerCmd customer) {
//		var cpf = customer.getCpf().replaceAll("-", "");
//		cpf = cpf.replaceAll(".", "");
		if (customerRepository.findByCpf(customer.getCpf()).isPresent()) {
			throw new CustomerCpfIsAlreadyInUse();
		}

		return customerRepository.save(customer.toCustomer());
	}

}
