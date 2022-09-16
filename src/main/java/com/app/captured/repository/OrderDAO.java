package com.app.captured.repository;

import java.time.LocalDate;
import java.util.List;

import com.app.captured.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
	public List<Order> findByDate(LocalDate date);
	
	
	@Query("select c from Customer c where c.customerId = customerId")
	public Customer getCustomerByOrderId(@Param("customerId") Integer customerId);
	

	
}