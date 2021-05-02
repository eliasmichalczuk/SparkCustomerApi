package com.elias.spark.customer.application;

import com.elias.spark.customer.application.cmd.CreateCustomerCmd;
import com.elias.spark.customer.application.cmd.UpdateCustomerCmd;
import com.elias.spark.customer.domain.exception.CustomerNotFoundException;
import com.elias.spark.customer.domain.model.Address;
import com.elias.spark.customer.domain.model.Customer;
import com.elias.spark.customer.domain.service.CustomerCpfDomainService;
import com.elias.spark.customer.domain.service.MainAddressDomainService;
import com.elias.spark.customer.repository.CustomerRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomerApplicationService {

	public static String PATH = "/customers";
	private CustomerRepository customerRepository;
	private MainAddressDomainService mainAddressDomainService;
	private CustomerCpfDomainService customerCpfDomainService;

	@Inject
	public CustomerApplicationService(CustomerRepository customerRepository,
	                                  MainAddressDomainService mainAddressDomainService,
	                                  CustomerCpfDomainService customerCpfDomainService) {
		this.customerRepository = customerRepository;
		this.mainAddressDomainService = mainAddressDomainService;
		this.customerCpfDomainService = customerCpfDomainService;
	}

	public Customer save(CreateCustomerCmd cmd) {

		customerCpfDomainService.verifyCpfInUse(cmd.getCpf());

		mainAddressDomainService.verifyOnlyOneMainAddress(cmd.getAddresses());

		return customerRepository.saveCustomerWithAddresses(cmd.toCustomer());
	}

	public Customer update(UpdateCustomerCmd cmd) throws CustomerNotFoundException {
		var customerToUpdate = customerRepository.getById(cmd.getId());

		customerCpfDomainService.verifyCanBeUsedInUpdate(cmd.getCpf(), customerToUpdate);
		mainAddressDomainService.verifyOnlyOneMainAddress(cmd.getAddresses());

		customerToUpdate.update(cmd.getName(), cmd.getBirthDate(), cmd.getCpf(), cmd.getGender(), cmd.getEmail());

		return customerRepository.update(cmd.toCustomer());
	}

	public Address saveAddress(Address address, Integer customerId) {
		address.setCustomerId(customerId);
		var addresses = customerRepository.findAllByCustomerId(customerId);
		addresses.stream().filter(Address::isMain).findFirst().ifPresent(addr -> {
			if (address.isMain()) {
				addr.setMain(false);
				customerRepository.updateAddress(addr);
			}
		});
		address.setId(customerRepository.insertAddress(address));
		return address;
	}

	public Address update(Address address, Integer customerId) {
		address.setCustomerId(customerId);
		var addresses = customerRepository.findAllByCustomerId(customerId);
		addresses.stream().filter(Address::isMain).findFirst().ifPresent(addr -> {
			if (address.isMain()) {
				addr.setMain(false);
				customerRepository.updateAddress(addr);
			}
		});
		customerRepository.updateAddress(address);
		return address;
	}

}
