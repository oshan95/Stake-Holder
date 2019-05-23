package com.virtusa.inventory.controller;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.inventory.modal.Customer;
import com.virtusa.inventory.modal.LoyaltyCard;
import com.virtusa.inventory.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public ResponseEntity<Customer> save(@Valid @RequestBody Customer customer) {
		if (customer == null) {
			System.out.println("customer is nulll");
		}
		return ResponseEntity.ok(customerService.save(customer));
	}

	@RequestMapping(value = "/details/{id}/loyalty", method = RequestMethod.POST)
	public ResponseEntity<Customer> createLoyalty(@PathVariable Integer id,
			@Valid @RequestBody LoyaltyCard loyaltyCard) {
		Optional<Customer> optionalCustomer = customerService.findOne(id);
		if (optionalCustomer.isPresent()) {

		}
		Customer customerUpdated = optionalCustomer.get();
		customerUpdated.setCard(loyaltyCard);
		return ResponseEntity.ok(customerService.save(customerUpdated));
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> fetchAll() {
		return ResponseEntity.ok(customerService.fetchAll());
	}

	@RequestMapping(value = "/details/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> update(@Valid @PathVariable Integer id, @RequestBody Customer customer) {

		Optional<Customer> optionalCustomer = customerService.findOne(id);
		if (!optionalCustomer.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		customer.setId(id);
		return ResponseEntity.ok(customerService.save(customer));

	}

	@RequestMapping(value = "/details/{id}", method = RequestMethod.DELETE)
	public HttpStatus delete(@Valid @PathVariable Integer id) {
		customerService.deleteCustomer(id);
		return HttpStatus.OK;
	}

}