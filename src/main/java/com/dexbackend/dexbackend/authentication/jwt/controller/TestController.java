package com.dexbackend.dexbackend.authentication.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class TestController {

	@GetMapping("/hello")
	public String hello() {
		
		return "TestController hello - v2";
		
	}
}
