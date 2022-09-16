package com.app.captured.service;

import java.time.LocalDate;
import java.util.List;

import com.app.captured.exception.*;
import com.app.captured.entity.Customer;
import com.app.captured.dto.OrderDTO;
import com.app.captured.entity.Order;

    public interface OrderService {
        public Order saveOrder(OrderDTO oDto,String token) throws LoginException, OrderException;
        
        public Order getOrderByOrderId(Integer OrderId) throws OrderException;
        
        public List<Order> getAllOrders() throws OrderException;
        
        public Order cancelOrderByOrderId(Integer OrderId,String token) throws OrderException;
        
        public Order updateOrderByOrder(OrderDTO order,Integer OrderId,String token) throws OrderException,LoginException;
        
        public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException;
    
        public Customer getCustomerByOrderId(Integer orderId) throws OrderException;
        
        //public Customer getCustomerIdByToken(String token) throws CustomerNotFoundException;
        
    
        
    }
