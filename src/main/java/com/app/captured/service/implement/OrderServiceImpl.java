package com.app.captured.service.implement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.captured.entity.*;
import com.app.captured.exception.*;
import com.app.captured.service.*;
import com.app.captured.repository.OrderDAO;
import com.app.captured.dto.OrderDTO;
import com.app.captured.dto.CartDTO;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO oDao;
	
	@Autowired
	private CustomerService cs;
	
	@Autowired
	private CartServiceImpl cartservicei;
	
	
	@Override
	public Order saveOrder(OrderDTO odto,String token) throws LoginException, OrderException {
		
		Order newOrder= new Order();
		
		Customer loggedInCustomer= cs.getLoggedInCustomerDetails(token);
		
		if(loggedInCustomer != null) {
			//Customer loggedInCustomer= cs.getLoggedInCustomerDetails(token);
			newOrder.setCustomer(loggedInCustomer);
			String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
			String userGivenCardNumber= odto.getCardNumber().getCardNumber();
			List<CartItem> productsInCart= loggedInCustomer.getCustomerCart().getCartItems();
			List<CartItem> productsInOrder = new ArrayList<>(productsInCart);
			
			newOrder.setOrdercartItems(productsInOrder);
			newOrder.setTotal(loggedInCustomer.getCustomerCart().getCartTotal());
			
			
			
			if(productsInCart.size()!=0) {
				if((usersCardNumber.equals(userGivenCardNumber)) 
						&& (odto.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity())
								&& (odto.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())))) {
					
					//System.out.println(usersCardNumber);
					newOrder.setCardNumber(odto.getCardNumber().getCardNumber());
					newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
					newOrder.setDate(LocalDate.now());
					System.out.println(usersCardNumber);
					List<CartItem> cartItemsList= loggedInCustomer.getCustomerCart().getCartItems();
					
					for(CartItem cartItem : cartItemsList ) {
						Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
						if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.SOLDOUT) {
							CartDTO cartDto = new CartDTO();
							cartDto.setProductId(cartItem.getCartProduct().getProductId());
							cartservicei.removeProductFromCart(cartDto, token);
							throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
						}
						cartItem.getCartProduct().setQuantity(remainingQuantity);
						if(cartItem.getCartProduct().getQuantity()==0) {
							cartItem.getCartProduct().setStatus(ProductStatus.SOLDOUT);
						}
					}
					cartservicei.clearCart(token);
					//System.out.println(newOrder);
					return oDao.save(newOrder);
				}
				else {
					System.out.println("Not same");
					newOrder.setCardNumber(null);
					newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
					newOrder.setDate(LocalDate.now());
					cartservicei.clearCart(token);
					return oDao.save(newOrder);
					
				}
			}
			else {
				throw new OrderException("No products in Cart");
			}
			
		}
		else {
			throw new LoginException("Invalid session token for customer"+"Please Login");
		}
	}

	@Override
	public Order getOrderByOrderId(Integer OrderId) throws OrderException {
		return oDao.findById(OrderId).orElseThrow(()-> new OrderException("No order exists with given OrderId "+ OrderId));
		
	}

	@Override
	public List<Order> getAllOrders() throws OrderException {
	
		List<Order> orders = oDao.findAll();
		if(orders.size()>0)
			return orders;
		else
			throw new OrderException("No Orders exists on your account");
	}

	@Override
	public Order cancelOrderByOrderId(Integer OrderId,String token) throws OrderException {
		Order order= oDao.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
		if(order.getCustomer().getCustomerId()==cs.getLoggedInCustomerDetails(token).getCustomerId()) {
			if(order.getOrderStatus()==OrderStatusValues.PENDING) {
				order.setOrderStatus(OrderStatusValues.CANCELLED);
				oDao.save(order);
				return order;
			}
			else if(order.getOrderStatus()==OrderStatusValues.SUCCESS) {
				order.setOrderStatus(OrderStatusValues.CANCELLED);
				List<CartItem> cartItemsList= order.getOrdercartItems();
				
				for(CartItem cartItem : cartItemsList ) {
					Integer addedQuantity = cartItem.getCartProduct().getQuantity()+cartItem.getCartItemQuantity();
					cartItem.getCartProduct().setQuantity(addedQuantity);
					if(cartItem.getCartProduct().getStatus() == ProductStatus.SOLDOUT) {
						cartItem.getCartProduct().setStatus(ProductStatus.AVAILABLE);
					}
				}
				
				oDao.save(order);
				return order;
			}
			else {
				throw new OrderException("Order was already cancelled");
			}
		}
		else {
			throw new LoginException("Invalid session token for customer"+"Please Login");
		}

		
	}

	@Override
	public Order updateOrderByOrder(OrderDTO orderDto, Integer OrderId,String token) throws OrderException,LoginException {
		Order existingOrder= oDao.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
		
		if(existingOrder.getCustomer().getCustomerId()==cs.getLoggedInCustomerDetails(token).getCustomerId()) {
			//existingOrder.setCardNumber(orderDto.getCardNumber().getCardNumber());
			//existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderDto.getAddressType()));
			Customer loggedInCustomer = cs.getLoggedInCustomerDetails(token);
			String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
			String userGivenCardNumber= orderDto.getCardNumber().getCardNumber();
//			System.out.println(loggedInCustomer);
			if((usersCardNumber.equals(userGivenCardNumber)) 
					&& (orderDto.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity())
							&& (orderDto.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())))) {
				existingOrder.setCardNumber(orderDto.getCardNumber().getCardNumber());
				existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderDto.getAddressType()));
				List<CartItem> cartItemsList= existingOrder.getOrdercartItems();
				for(CartItem cartItem : cartItemsList ) {
					Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
					if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.SOLDOUT) {
						CartDTO cartDto = new CartDTO();
						cartDto.setProductId(cartItem.getCartProduct().getProductId());
						cartservicei.removeProductFromCart(cartDto, token);
						throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
					}
					cartItem.getCartProduct().setQuantity(remainingQuantity);
					if(cartItem.getCartProduct().getQuantity()==0) {
						cartItem.getCartProduct().setStatus(ProductStatus.SOLDOUT);
					}
				}
				return oDao.save(existingOrder);
			}
			else {
				throw new OrderException("Incorrect Card Number Again" + usersCardNumber + userGivenCardNumber);
			}
			
			
		}
		else {
			throw new LoginException("Invalid session token for customer"+"Kindly Login");
		}
		
	}

	@Override
	public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException {
		
		List<Order> listOfOrdersOntheDay= oDao.findByDate(date);
		return listOfOrdersOntheDay;
	}

	@Override
	public Customer getCustomerByOrderId(Integer orderId) throws OrderException {
		Optional<Order> opt= oDao.findById(orderId);
		if(opt.isPresent()) {
			Order existingOrder= opt.get();
			
			return oDao.getCustomerByOrderId(existingOrder.getCustomer().getCustomerId());
		}
		else
			throw new OrderException("No Order exists with orderId "+orderId);
	}

}   