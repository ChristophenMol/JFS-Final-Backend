package com.app.captured.controllers;

public class ProductNotFound extends RuntimeException{

	public ProductNotFound() {
	}
    
	public ProductNotFound(String message) {
		super(message);
	}

}