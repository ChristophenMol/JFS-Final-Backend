package com.app.captured.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.captured.entity.Cart;
import com.app.captured.entity.Product;

@Repository
public interface CartDAO extends JpaRepository<Cart,Integer> {

	public Map<Product,Integer> findbyName(String productName);
	public Cart findbyId(Integer cartId);
}
