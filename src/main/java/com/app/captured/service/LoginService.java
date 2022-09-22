package com.app.captured.service;

import com.app.captured.dto.SessionDTO;
import com.app.captured.dto.CustomerDTO;
import com.app.captured.dto.SellerDTO;
import com.app.captured.entity.UserSession;


public interface LoginService {
	
	public UserSession loginCustomer(CustomerDTO customer);
	
	public SessionDTO logoutCustomer(SessionDTO session);
	
	public void checkTokenStatus(String token);
	
	public void deleteExpiredTokens();
	
	
	public UserSession loginSeller(SellerDTO seller);
	
	public SessionDTO logoutSeller(SessionDTO session);
	
	
}