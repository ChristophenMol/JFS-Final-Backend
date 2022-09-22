package com.app.captured.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.captured.entity.*;
import com.app.captured.exception.*;
import com.app.captured.service.*;
import com.app.captured.repository.SellerDAO;
import com.app.captured.repository.SessionDAO;
import com.app.captured.dto.SellerDTO;
import com.app.captured.dto.SessionDTO;


@Service
public class SellerServiceImpl implements SellerService {
	
	@Autowired
	private SellerDAO sellerDao;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SessionDAO sessionDao;
	

	@Override
	public Seller addSeller(Seller seller) {
		
		Seller add = sellerDao.save(seller);
		
		return add;
	}

	@Override
	public List<Seller> getAllSellers() throws SellerException {
		
		List<Seller> sellers= sellerDao.findAll();
		
		if(sellers.size()>0) {
			return sellers;
		}
		else throw new SellerException("No Seller Found!");
		
	}

	@Override
	public Seller getSellerById(Integer sellerId) {
		
		Optional<Seller> seller=sellerDao.findById(sellerId);
		
		if(seller.isPresent()) {
			return seller.get();
		}
		else throw new SellerException("Seller not found for this ID: "+sellerId);
	}

	@Override
	public Seller updateSeller(Seller seller, String token) {
		
		if(token.contains("seller") == false) {
			throw new LoginException("Invalid session token for seller");
		}
		
		loginService.checkTokenStatus(token);
		
		Seller existingSeller = sellerDao.findById(seller.getSellerId()).orElseThrow(()-> new SellerException("Seller not found for this Id: "+seller.getSellerId()));
		Seller newSeller = sellerDao.save(seller);
		return newSeller;
	}

	@Override
	public Seller deleteSellerById(Integer sellerId, String token) {
		
		if(token.contains("seller") == false) {
			throw new LoginException("Invalid session token for seller");
		}
		
		loginService.checkTokenStatus(token);
		
		Optional<Seller> opt=sellerDao.findById(sellerId);
		
		if(opt.isPresent()) {
			
			UserSession user = sessionDao.findByToken(token).get();
			
			Seller existingSeller = opt.get();
			
			if(user.getUserId() == existingSeller.getSellerId()) {
				sellerDao.delete(existingSeller);
				
				// logic to log out a seller after he deletes his account
				SessionDTO session = new SessionDTO();
				session.setToken(token);
				loginService.logoutSeller(session);
				
				return existingSeller;
			}
			else {
				throw new SellerException("Verification error in deleting seller account");
			}
		}
		else throw new SellerException("Seller not found for this ID: "+sellerId);
		
	}

	@Override
	public Seller updateSellerEmail(SellerDTO sellerDto, String token) throws SellerException {
		
		if(token.contains("seller") == false) {
			throw new LoginException("Invalid session token for seller");
		}
		
		loginService.checkTokenStatus(token);
		
		UserSession user = sessionDao.findByToken(token).get();
		
		Seller existingSeller = sellerDao.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID: "+ user.getUserId()));
		
		if(existingSeller.getPassword().equals(sellerDto.getPassword())) {
			existingSeller.setEmailId(sellerDto.getEmailId());
			return sellerDao.save(existingSeller);
		}
		else {
			throw new SellerException("Error occurred in updating e-mail. Please enter correct password");
		}
		
	}

	@Override
	public Seller getSellerByEmailId(String emailId, String token) throws SellerException {
		
		if(token.contains("seller") == false) {
			throw new LoginException("Invalid session token for seller");
		}
		
		loginService.checkTokenStatus(token);
		
		Seller existingSeller = sellerDao.findByEmailId(emailId).orElseThrow( () -> new SellerException("Seller not found with given email"));
		
		return existingSeller;
	}
	
	@Override
	public Seller getCurrentlyLoggedInSeller(String token) throws SellerException{
		
		if(token.contains("seller") == false) {
			throw new LoginException("Invalid session token for seller");
		}
		
		loginService.checkTokenStatus(token);
		
		UserSession user = sessionDao.findByToken(token).get();
		
		Seller existingSeller=sellerDao.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID"));
		
		return existingSeller;
		
	}
	
	
	// Method to update password - based on current token
	
	@Override
	public SessionDTO updateSellerPassword(SellerDTO sellerDTO, String token) {
				
		if(token.contains("seller") == false) {
			throw new LoginException("Invalid session token for seller");
		}
			
			
		loginService.checkTokenStatus(token);
			
		UserSession user = sessionDao.findByToken(token).get();
			
		Optional<Seller> opt = sellerDao.findById(user.getUserId());
			
		if(opt.isEmpty())
			throw new SellerException("Seller does not exist");
			
		Seller existingSeller = opt.get();
			
			
		if(sellerDTO.getEmailId().equals(existingSeller.getEmailId()) == false) {
			throw new SellerException("Verification error. Mobile number does not match");
		}
			
		existingSeller.setPassword(sellerDTO.getPassword());
			
		sellerDao.save(existingSeller);
			
		SessionDTO session = new SessionDTO();
			
		session.setToken(token);
			
		loginService.logoutSeller(session);
			
		session.setMessage("Updated password and logged out. Login again with new password");
			
		return session;

	}

}