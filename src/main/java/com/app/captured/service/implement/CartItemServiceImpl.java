package com.app.captured.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.captured.exception.ProductNotFoundException;
import com.app.captured.dto.CartDTO;
import com.app.captured.entity.CartItem;
import com.app.captured.entity.Product;
import com.app.captured.entity.ProductStatus;
import com.app.captured.repository.ProductDAO;
import com.app.captured.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	ProductDAO productDao;

	@Override
	public CartItem createItemForCart(CartDTO cartDto) {
		
		Product existingProduct = productDao.findById(cartDto.getProductId()).orElseThrow( () -> new ProductNotFoundException("Product Not found"));
		
		if(existingProduct.getStatus().equals(ProductStatus.SOLDOUT) || existingProduct.getQuantity() == 0) {
			throw new ProductNotFoundException("Product OUT OF STOCK");
		}
		
		CartItem newItem = new CartItem();
		
		newItem.setCartItemQuantity(1);
		
		newItem.setCartProduct(existingProduct);
		
		return newItem;
	}
}