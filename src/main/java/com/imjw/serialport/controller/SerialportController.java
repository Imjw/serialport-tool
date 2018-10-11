package com.imjw.serialport.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imjw.serialport.service.SerialportService;

@RestController
@RequestMapping("/serialport")
public class SerialportController {

	@Autowired
	SerialportService serialportService;
	
	@GetMapping("/weight")
	public Map<String,String> queryWeight() {
		Map<String,String> result = new HashMap<>();
		result.put("weight", serialportService.queryWeight());
		return result;
	}
	
}
