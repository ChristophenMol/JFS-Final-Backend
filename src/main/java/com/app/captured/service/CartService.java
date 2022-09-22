package com.app.captured.service;

import java.util.List;

import com.app.captured.controllers.ProductNotFound;
import com.app.captured.exception.CartItemNotFound;
import com.app.captured.entity.*;
import com.app.captured.dto.CartDTO;

public interface CartService {
	
	public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound;
	public Cart getCartProduct(String token);
	public Cart removeProductFromCart(CartDTO cartDto,String token) throws ProductNotFound;
	public Cart changeQuantity(Product product,Customer customer,Integer quantity);
	
	public Cart clearCart(String token);
	
}