package com.app.captured.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SellerDTO {
	
	
	@NotNull(message="Please enter your email address")
	@Pattern(regexp="[6789]{1}[0-9]{9}")
	private String emailId;
	
	
	@Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
	private String password;
	
}