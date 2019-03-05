package com.xxyp.admin.web.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * R:ajax返回消息体
 * @author guopeng1
 * 2019年1月25日16:21:35
 */
public class AjaxResultDto implements Serializable{

	// 0失败 1成功 2跳转
	private Integer status;
	//返回消息
	private String msg;
	
	//需要跳转的url
	private String url;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getJsonString() {
		Map map=new HashMap<>();
		map.put("status", status);
		map.put("url", url);
		map.put("msg", msg);
		return JSON.toJSONString(map);
	}
	
	
}
