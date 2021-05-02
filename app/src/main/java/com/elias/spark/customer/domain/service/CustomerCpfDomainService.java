package com.elias.spark.customer.domain.service;

import com.elias.spark.customer.domain.exception.CustomerCpfIsAlreadyInUseException;
import com.elias.spark.customer.domain.model.Customer;
import com.elias.spark.customer.repository.CustomerRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomerCpfDomainService {

	private final CustomerRepository customerRepository;

	@Inject
	public CustomerCpfDomainService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	public void verifyCpfInUse(String cpf) {
		if (customerRepository.findByCpf(cpf).isPresent()) {
			throw new CustomerCpfIsAlreadyInUseException();
		}
	}

	public void verifyCanBeUsedInUpdate(String cpf, Customer customerToUpdate) {
		if (customerRepository.findByCpf(cpf).isPresent() && customerToUpdate.getCpf() != cpf) {
			throw new CustomerCpfIsAlreadyInUseException();
		}
	}
}
