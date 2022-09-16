package com.app.captured.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
	
	private String token;
	
	private String message;
}