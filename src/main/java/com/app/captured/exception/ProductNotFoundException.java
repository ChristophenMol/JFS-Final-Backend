package com.app.captured.exception;

public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException() {
	}
	
	public ProductNotFoundException(String message){
		super(message);
	}
}