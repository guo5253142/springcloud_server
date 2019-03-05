package com.xxyp.admin.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json工具类
 * 
 */
public class JsonUtils {
	public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	public static String toJson(Object value) {
		return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
	}
	
	public static <T> T fromJson(String value, Class<T> clazz) {
		return JSON.parseObject(value, clazz);
	}
	
	/**
	 * 
	 * @param is
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(InputStream is, Class<T> clazz) {
		try {
			return JSON.parseObject(is, clazz);
		} catch (IOException e) {
			logger.error("反序列化Json异常", e);
		}
		return null;
	}
	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}
	public static void main(String[] args)
	{
		InputStream   aaa   =   new   ByteArrayInputStream("aaaaaaaaaaaaaaaa".getBytes());  
		try {
			String temp = inputStream2String(aaa);
			System.out.println(temp);
			
			String temp1 = inputStream2String(aaa);
			System.out.println(temp1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
	}
}
