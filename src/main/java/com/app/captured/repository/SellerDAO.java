package com.app.captured.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.captured.entity.Seller;

public interface SellerDAO extends JpaRepository<Seller, Integer> {
	
	Optional<Seller> findByEmailId(String emailId);
	
}