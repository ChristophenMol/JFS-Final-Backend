package com.app.captured.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.captured.service.OrderService;
import com.app.captured.entity.Order;
import com.app.captured.dto.OrderDTO;
import com.app.captured.repository.OrderDAO;
import com.app.captured.entity.Customer;

    @RestController
public class OrderController {
	@Autowired
	private OrderDAO oDao;
	
	@Autowired
	private OrderService oService;
	
	@PostMapping("/order/place")
	public ResponseEntity<Order> addTheNewOrder(@Valid @RequestBody OrderDTO oDto,@RequestHeader("token") String token){
		
		Order savedOrder = oService.saveOrder(oDto,token);
		return new ResponseEntity<Order>(savedOrder,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/orders")
	public List<Order> getAllOrders(){
		
		
		List<Order> listOfAllOrders = oService.getAllOrders();
		return listOfAllOrders;
		
	}
	
	@GetMapping("/orders/{orderId}")
	public Order getOrdersByOrderId(@PathVariable("orderId") Integer orderId) {
		
		return oService.getOrderByOrderId(orderId);
		
	}
	
	@DeleteMapping("/orders/{orderId}")
	public Order cancelTheOrderByOrderId(@PathVariable("orderId") Integer orderId,@RequestHeader("token") String token){
		
		return oService.cancelOrderByOrderId(orderId,token);
	}
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<Order> updateOrderByOrder(@Valid @RequestBody OrderDTO orderDto, @PathVariable("orderId") Integer orderId,@RequestHeader("token") String token){
		
		Order updatedOrder= oService.updateOrderByOrder(orderDto,orderId,token);
		
		return new ResponseEntity<Order>(updatedOrder,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/orders/by/date")
	public List<Order> getOrdersByDate(@RequestParam("date") String date){
		
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ld=LocalDate.parse(date,dtf);
		return oService.getAllOrdersByDate(ld);
	}
	
	@GetMapping("/customer/{orderId}")
	public Customer getCustomerDetailsByOrderId(@PathVariable("orderId") Integer orderId) {
		return oService.getCustomerByOrderId(orderId);
	}

}
