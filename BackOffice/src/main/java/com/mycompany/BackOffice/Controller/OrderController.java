package com.mycompany.BackOffice.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.BackOffice.dto.order.OrderInfo;
import com.mycompany.BackOffice.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/order")
public class OrderController {
	@Resource
	OrderService orderService;
	
	
	@RequestMapping("/orderList")
	public String orderList(Model model) {
		log.info("Run orderList");
		List<OrderInfo> orderList = orderService.getOrderInfoList();
		model.addAttribute("orderList", orderList);
		return "order/orderList";
	}
	
	@RequestMapping("/orderDetail")
	public String orderDetail(
			String oid,
			Model model) {
		log.info("Run orderDetail");
		model.addAttribute("data", orderService.getOrderInfo(oid));
		return "order/orderDetail";
	}
}
