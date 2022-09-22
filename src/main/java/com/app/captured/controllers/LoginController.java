package com.app.captured.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.captured.entity.*;
import com.app.captured.dto.CustomerDTO;

import com.app.captured.dto.SellerDTO;
import com.app.captured.dto.SessionDTO;
import com.app.captured.service.CustomerService;
import com.app.captured.service.LoginService;
import com.app.captured.service.SellerService;

@RestController
public class LoginController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SellerService sellerService;

	
	// register a new customer
	
	@PostMapping(value = "/register/customer", consumes = "application/json")
	public ResponseEntity<Customer> registerAccountHandler(@Valid @RequestBody Customer customer) {
		return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
	}
	
	// login a user
	
	@PostMapping(value = "/login/customer", consumes = "application/json")
	public ResponseEntity<UserSession> loginCustomerHandler(@Valid @RequestBody CustomerDTO customerDto){
		return new ResponseEntity<>(loginService.loginCustomer(customerDto), HttpStatus.ACCEPTED);
	}
	
	
	// logout a user
	
	@PostMapping(value = "/logout/customer", consumes = "application/json")
	public ResponseEntity<SessionDTO> logoutCustomerHandler(@RequestBody SessionDTO sessionToken){
		return new ResponseEntity<>(loginService.logoutCustomer(sessionToken), HttpStatus.ACCEPTED);
	}
	
	
	
	
	/*********** SELLER REGISTER LOGIN LOGOUT HANDLER ************/
	
	@PostMapping(value = "/register/seller", consumes = "application/json")
	public ResponseEntity<Seller> registerSellerAccountHandler(@Valid @RequestBody Seller seller) {
		return new ResponseEntity<>(sellerService.addSeller(seller), HttpStatus.CREATED);
	}
	
	
	// login a user
	
	@PostMapping(value = "/login/seller", consumes = "application/json")
	public ResponseEntity<UserSession> loginSellerHandler(@Valid @RequestBody SellerDTO seller){
		return new ResponseEntity<>(loginService.loginSeller(seller), HttpStatus.ACCEPTED);
	}
		
		
	// logout a user
		
	@PostMapping(value = "/logout/seller", consumes = "application/json")
	public ResponseEntity<SessionDTO> logoutSellerHandler(@RequestBody SessionDTO sessionToken){
		return new ResponseEntity<>(loginService.logoutSeller(sessionToken), HttpStatus.ACCEPTED);
	}
	
}