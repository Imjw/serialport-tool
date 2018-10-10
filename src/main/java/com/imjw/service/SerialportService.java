package com.imjw.service;

import org.springframework.stereotype.Service;

import com.imjw.serialport.util.SerialPortUtil;

@Service
public class SerialportService {

	public String queryWeight() {
		return SerialPortUtil.weightValue;
	}
	
	
}
