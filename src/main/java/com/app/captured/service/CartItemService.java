package com.app.captured.service;

import com.app.captured.dto.CartDTO;
import com.app.captured.entity.CartItem;

public interface CartItemService {
	
	public CartItem createItemForCart(CartDTO cartDto);
	
}