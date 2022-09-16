package com.app.captured.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.captured.entity.Address;

@Repository
public interface AddressDAO extends JpaRepository<Address, Integer>{

}