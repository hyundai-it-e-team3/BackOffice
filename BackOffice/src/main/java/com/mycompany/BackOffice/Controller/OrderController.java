package com.mycompany.BackOffice.Controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.BackOffice.dto.order.OrderDetail;
import com.mycompany.BackOffice.dto.order.PagerAndOrderInfo;
import com.mycompany.BackOffice.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/order")
public class OrderController {
	@Resource
	OrderService orderService;
	
	@RequestMapping("/orderList")
	public String orderList(@RequestParam(defaultValue="1") int pageNo, Model model) {
		PagerAndOrderInfo data = orderService.getOrderInfoList(pageNo);
		model.addAttribute("orderList", data.getOrderInfos());
		model.addAttribute("pager", data.getPager());
		return "order/orderList";
	}
	
	@RequestMapping("/orderDetail")
	public String orderDetail(
			String oid,
			Model model) {
		model.addAttribute("data", orderService.getOrderInfo(oid));
		return "order/orderDetail";
	}
	
	@RequestMapping("/changestate")
	public String changeState(
			OrderDetail orderDetail,
			Model model) {
		log.info(orderDetail.toString());
		orderService.updateOdState(orderDetail);
		return "redirect:/order/orderDetail?oid="+orderDetail.getOrderId();
	}
	
	@RequestMapping("/orderSearch")
	public String orderSearch(@RequestParam(defaultValue="1") int pageNo, Model model) {
		PagerAndOrderInfo data = orderService.getOrderInfoList(pageNo);
		model.addAttribute("orderList", data.getOrderInfos());
		model.addAttribute("pager", data.getPager());
		return "order/orderList";
	}
	
	@RequestMapping("/returnOrder")
	public String returnOrder(
			String orderId,
			Model model) {
		log.info(orderId);
		orderService.returnOrder(orderId);
		return "redirect:/order/orderDetail?oid="+orderId;
	}
	
}
