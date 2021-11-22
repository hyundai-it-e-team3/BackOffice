package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/promotion")
public class PromotionController {
	@RequestMapping("/registerWithEvent")
	public String registerWithEvent() {
		log.info("Run RegisterWithEvent");
		return "/promotion/registerWithEvent";
	}
	
	@RequestMapping("/eventList")
	public String eventList() {
		log.info("Run EventList");
		return "/promotion/eventList";
	}
	
	@RequestMapping("/registerWithCoupon")
	public String registerWithCoupon() {
		log.info("Run RegisterWithCoupon");
		return "/promotion/registerWithCoupon";
	}
	
	@RequestMapping("/couponList")
	public String couponList() {
		log.info("Run CouponList");
		return "/promotion/couponList";
	}
}
