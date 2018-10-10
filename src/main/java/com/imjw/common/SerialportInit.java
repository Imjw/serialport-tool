package com.imjw.common;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.imjw.serialport.util.SerialPortUtil;
import com.imjw.serialport.util.SerialPortUtil.DataAvailableListener;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

@Component
public class SerialportInit implements InitializingBean{

	SerialPort serialport;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initPortListener();
	}
	
	public void initPortListener() {
		List<String> commNames = SerialPortUtil.findPorts();
		System.out.println(commNames);
		if (commNames.isEmpty()) {
            System.out.println("没有搜索到有效串口！");
        } else {
            try {
            	serialport = SerialPortUtil.openPort("COM3", 9600);
                if (serialport != null) {
                    System.out.println(serialport.getName()+"串口已打开");
                }
            } catch (PortInUseException e) {
            	System.out.println("串口已被占用！");
            }
        }
		// 添加串口监听
		SerialPortUtil.addListener(serialport, new DataAvailableListener() {

            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (serialport == null) {
                        System.out.println("串口对象为空，监听失败！");
                    } else {
                        // 读取串口数据
                        data = SerialPortUtil.readFromPort(serialport);

                        // 以字符串的形式接收数据
                        String weight = transDate(data);
                        System.out.println(weight);
                        SerialPortUtil.weightValue = weight;
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
	}
	
	public String transDate(byte[] data) {
		String result = "";
		try {
			result = new String(data, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String regEx="[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(result); 
		result = m.replaceAll("").trim();
		System.out.println(result);
		return result;
	}

}
