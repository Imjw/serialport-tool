package com.imjw.common;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.imjw.serialport.util.SerialPortUtil;
import com.imjw.serialport.util.SerialPortUtil.DataAvailableListener;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

@Component
public class SerialportInit implements InitializingBean{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	SerialPort serialport;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initPortListener();
	}
	
	public void initPortListener() {
		List<String> commNames = SerialPortUtil.findPorts();
		logger.info("扫描到有效串口：{}",commNames);
		if (commNames.isEmpty()) {
			logger.error("没有搜索到有效串口！");
            return;
        } else {
            try {
            	serialport = SerialPortUtil.openPort("COM3", 9600);
                if (serialport != null) {
                	logger.info("串口[{}]已打开",serialport.getName());
                }
            } catch (PortInUseException e) {
            	logger.error("串口已被占用！");
            }
        }
		// 添加串口监听
		SerialPortUtil.addListener(serialport, new DataAvailableListener() {

            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (serialport == null) {
                    	logger.info("串口对象为空，监听失败！");
                    } else {
                        // 读取串口数据
                        data = SerialPortUtil.readFromPort(serialport);

                        // 以字符串的形式接收数据
                        String weight = transDate(data);
                        logger.info("获取重量值：{}", weight);
                        SerialPortUtil.weightValue = weight;
                    }
                } catch (Exception e) {
                	logger.error("读取串口数据异常！",e);
                }
            }
        });
	}
	
	public String transDate(byte[] data) {
		String result = "";
		try {
			result = new String(data, "GBK");
		} catch (UnsupportedEncodingException e) {
			logger.error("byte[] to String failed !", e);
			return "";
		}
		logger.info("serialport info : {}",result.replaceAll("[\\s]", ""));
		return result.replaceAll("[^0-9.]", "");
	}

}
