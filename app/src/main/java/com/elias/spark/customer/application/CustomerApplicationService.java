package com.elias.spark.customer.application;

import com.elias.spark.customer.application.cmd.CreateCustomerCmd;
import com.elias.spark.customer.application.cmd.UpdateCustomerCmd;
import com.elias.spark.customer.domain.exception.CustomerNotFoundException;
import com.elias.spark.customer.domain.model.Address;
import com.elias.spark.customer.domain.model.Customer;
import com.elias.spark.customer.domain.service.CustomerCpfDomainService;
import com.elias.spark.customer.domain.service.MainAddressDomainService;
import com.elias.spark.customer.repository.CustomerRepository;
import com.elias.spark.customer.repository.IAddressRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomerApplicationService {

	public static String PATH = "/customers";
	private CustomerRepository customerRepository;
	private MainAddressDomainService mainAddressDomainService;
	private CustomerCpfDomainService customerCpfDomainService;
	private final IAddressRepository addressRepository;

	@Inject
	public CustomerApplicationService(CustomerRepository customerRepository,
	                                  MainAddressDomainService mainAddressDomainService,
	                                  CustomerCpfDomainService customerCpfDomainService,
	                                  IAddressRepository addressRepository) {
		this.customerRepository = customerRepository;
		this.mainAddressDomainService = mainAddressDomainService;
		this.customerCpfDomainService = customerCpfDomainService;
		this.addressRepository = addressRepository;
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
				addressRepository.updateAddress(addr);
			}
		});
		address.setId(addressRepository.insertAddress(address));
		return address;
	}

	public Address update(Address address, Integer customerId) {
		address.setCustomerId(customerId);
		var addresses = customerRepository.findAllByCustomerId(customerId);
		addresses.stream().filter(Address::isMain).findFirst().ifPresent(addr -> {
			if (address.isMain()) {
				addr.setMain(false);
				addressRepository.updateAddress(addr);
			}
		});
		addressRepository.updateAddress(address);
		return address;
	}

}
