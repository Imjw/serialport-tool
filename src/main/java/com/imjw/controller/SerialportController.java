package com.imjw.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serialport")
public class SerialportController {

	@GetMapping("/weight")
	public Map<String,Object> getWeight() {
		
		return null;
	}
	
}
