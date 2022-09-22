package com.app.captured.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.captured.entity.*;
import com.app.captured.dto.CustomerDTO;
import com.app.captured.dto.CustomerUpdateDTO;
import com.app.captured.dto.SessionDTO;
import com.app.captured.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	// get list of all customers
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomersHandler(@RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.getAllCustomers(token), HttpStatus.ACCEPTED);
	}
	
	
	// get customer details of currently logged in user - sends data as per token
	
	@GetMapping("/customer/current")
	public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(@RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.getLoggedInCustomerDetails(token), HttpStatus.ACCEPTED);
	}
	
	
	// update customer
	
	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.updateCustomer(customerUpdate, token), HttpStatus.ACCEPTED);
	}
	
	
	// update customer email-id or mobile no
	@PutMapping("/customer/update/credentials")
	public ResponseEntity<Customer> updateCustomerMobileEmailHandler(@Valid @RequestBody CustomerUpdateDTO customerUpdate, @RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.updateCustomerMobileNoOrEmailId(customerUpdate, token), HttpStatus.ACCEPTED);
	}
	
	
	// update customer password
	@PutMapping("/customer/update/password")
	public ResponseEntity<SessionDTO> updateCustomerPasswordHandler(@Valid @RequestBody CustomerDTO customerDto, @RequestHeader("token") String token){		
		return new ResponseEntity<>(customerService.updateCustomerPassword(customerDto, token), HttpStatus.ACCEPTED);
	}
	
	
	// update new customer Address
	@PutMapping("/customer/update/address")
	public ResponseEntity<Customer> updateAddressHandler(@Valid @RequestBody Address address, @RequestParam("type") String type, @RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.updateAddress(address, type, token), HttpStatus.ACCEPTED);
	}
	
	
	// update Credit card details
	@PutMapping("/customer/update/card")
	public ResponseEntity<Customer> updateCreditCardHandler(@RequestHeader("token") String token, @Valid @RequestBody CreditCard newCard){
		return new ResponseEntity<>(customerService.updateCreditCardDetails(token, newCard), HttpStatus.ACCEPTED);
	}
	
	
	// remove a user Address
	@DeleteMapping("/customer/delete/address")
	public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("type") String type, @RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.deleteAddress(type, token), HttpStatus.ACCEPTED);
	}
	
	// delete customer
	@DeleteMapping("/customer")
	public ResponseEntity<SessionDTO> deleteCustomerHandler(@Valid @RequestBody CustomerDTO customerDto, @RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.deleteCustomer(customerDto, token), HttpStatus.ACCEPTED);
	}
	
	
	
	@GetMapping("/customer/orders")
	public ResponseEntity<List<Order>> getCustomerOrdersHandler(@RequestHeader("token") String token){
		return new ResponseEntity<>(customerService.getCustomerOrders(token), HttpStatus.ACCEPTED);
	}
}