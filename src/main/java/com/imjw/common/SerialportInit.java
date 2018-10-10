package com.imjw.common;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.imjw.serialport.util.ByteUtils;
import com.imjw.serialport.util.SerialPortUtil;
import com.imjw.serialport.util.SerialPortUtil.DataAvailableListener;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

@Component
public class SerialportInit implements InitializingBean{

	private SerialPort serialport;
	
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
                        System.out.println(transDate(data));
                        // 以十六进制的形式接收数据
                        System.out.println(ByteUtils.byteArrayToHexString(data));
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
	}
	
	@CachePut(value = "weight", key = "weight")
	public String transDate(byte[] data) {
		return new String(data);
	}

}
