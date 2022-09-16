package com.app.captured.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.captured.entity.Cart;
import com.app.captured.dto.CartDTO;
import com.app.captured.entity.CartItem;
import com.app.captured.repository.CartDAO;
import com.app.captured.repository.CustomerDAO;
import com.app.captured.service.CartService;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartDAO cartDao;
	
	@Autowired
	private CustomerDAO customerDao;
	

	@PostMapping(value = "/cart/add")
	public ResponseEntity<Cart> addProductToCartHandler(@RequestBody CartDTO cartDto ,@RequestHeader("token")String token){
		
		Cart cart = cartService.addProductToCart(cartDto, token);
		return new ResponseEntity<Cart>(cart,HttpStatus.CREATED);
	}
	
//	
	@GetMapping(value = "/cart")
	public ResponseEntity<Cart> getCartProductHandler(@RequestHeader("token")String token){
		return new ResponseEntity<>(cartService.getCartProduct(token), HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping(value = "/cart")
	public ResponseEntity<Cart> removeProductFromCartHandler(@RequestBody CartDTO cartDto ,@RequestHeader("token")String token){
		
		Cart cart = cartService.removeProductFromCart(cartDto, token);
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/cart/clear")
	public ResponseEntity<Cart> clearCartHandler(@RequestHeader("token") String token){
		return new ResponseEntity<>(cartService.clearCart(token), HttpStatus.ACCEPTED);
	}
	
	
}