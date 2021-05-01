package com.elias.spark.customer.application;

import com.elias.spark.customer.application.cmd.CreateCustomerCmd;
import com.elias.spark.customer.application.cmd.UpdateCustomerCmd;
import com.elias.spark.customer.domain.Customer;
import com.elias.spark.customer.domain.exception.CustomerCpfIsAlreadyInUseException;
import com.elias.spark.customer.repository.CustomerRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javassist.NotFoundException;

@Singleton
public class CustomerApplicationService {

	public static String PATH = "/customers";
	private CustomerRepository customerRepository;

	@Inject
	public CustomerApplicationService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer save(CreateCustomerCmd cmd) {

		if (customerRepository.findByCpf(cmd.getCpf()).isPresent()) {
			throw new CustomerCpfIsAlreadyInUseException();
		}

		return customerRepository.save(cmd.toCustomer());
	}

	public Customer update(UpdateCustomerCmd cmd) throws NotFoundException {
		var oldCustomer = customerRepository.findById(cmd.getId())
		                                    .orElseThrow(() -> new NotFoundException("Customer not found."));

		if (cpfCannotBeUsedInUpdate(cmd, oldCustomer)) {
			throw new CustomerCpfIsAlreadyInUseException();
		}

		oldCustomer.update(cmd.getName(), cmd.getBirthDate(), cmd.getCpf(), cmd.getGender(), cmd.getEmail());

		return customerRepository.update(cmd.toCustomer());
	}

	private boolean cpfCannotBeUsedInUpdate(UpdateCustomerCmd cmd, Customer oldCustomer) {
		return customerRepository.findByCpf(cmd.getCpf()).isPresent() && oldCustomer.getCpf() != cmd.getCpf();
	}
}

//var cpf = customer.getCpf().replaceAll("-", "");
//cpf = cpf.replaceAll(".", "");