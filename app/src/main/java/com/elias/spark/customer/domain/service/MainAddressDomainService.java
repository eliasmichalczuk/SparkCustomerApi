package com.elias.spark.customer.domain.service;

import java.util.List;

import com.elias.spark.customer.domain.exception.AddressOnlyOneAddressCanBeMainException;
import com.elias.spark.customer.domain.model.Address;
import com.elias.spark.customer.repository.CustomerRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MainAddressDomainService {

	private final CustomerRepository customerRepository;

	@Inject
	public MainAddressDomainService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	public void verifyOnlyOneMainAddress(List<Address> addresses) {
		if (addresses.stream().filter(Address::isMain).count() > 1) {
			throw new AddressOnlyOneAddressCanBeMainException();
		}
	}
}
