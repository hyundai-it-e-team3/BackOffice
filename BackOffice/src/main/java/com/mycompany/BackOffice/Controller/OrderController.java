package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/order")
public class OrderController {
	@RequestMapping("/orderList")
	public String orderList() {
		log.info("Run orderList");
		
		return "order/orderList";
	}
	
	@RequestMapping("/orderDetail")
	public String orderDetail() {
		log.info("Run orderDetail");
		
		return "order/orderDetail";
	}
}
