package com.imjw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imjw.serialport.util.SerialPortUtil;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

@Service
public class SerialportService {

	public void openSerialPort() {
		List<String> commNames = SerialPortUtil.findPorts();
		
		if (commNames.isEmpty()) {
            System.out.println("没有搜索到有效串口！");
        } else {
            try {
            	SerialPort serialport = SerialPortUtil.openPort("COM3", 9600);
                if (serialport != null) {
                    System.out.println(serialport.getName()+"串口已打开");
                }
            } catch (PortInUseException e) {
            	System.out.println("串口已被占用！");
            }
        }
	}
	
	
}
