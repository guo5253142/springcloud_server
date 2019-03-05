package com.xxyp.sso.server.schedule;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxyp.sso.server.common.model.Ticket;



public class RecoverTicket implements Runnable {
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Ticket> tickets;
	
	public RecoverTicket(Map<String, Ticket> tickets) {
		super();
		this.tickets = tickets;
	}

	@Override
	public void run() {
		logger.info("执行任务：检查是否有ticket过期");
		List<String> ticketKeys = new ArrayList<String>();
		for(Entry<String, Ticket> entry : tickets.entrySet()) {
			if(entry.getValue().getRecoverTime().getTime() < System.currentTimeMillis())
				ticketKeys.add(entry.getKey());
		}
		for(String ticketKey : ticketKeys) {
			tickets.remove(ticketKey);
			logger.info("ticket[" + ticketKey + "]过期已删除！");
		}
	}

}
