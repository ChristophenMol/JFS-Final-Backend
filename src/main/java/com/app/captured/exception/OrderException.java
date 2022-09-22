package com.app.captured.exception;

public class OrderException extends RuntimeException{ 
	public OrderException() {
		
	}
	public OrderException(String message) {
		super(message);
	}

}