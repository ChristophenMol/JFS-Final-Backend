package com.app.captured.repository;

import com.app.captured.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemDAO extends JpaRepository<CartItem, Integer>{

}
