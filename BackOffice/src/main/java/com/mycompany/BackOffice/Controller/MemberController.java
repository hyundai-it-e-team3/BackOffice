package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
	@RequestMapping("/memberList")
	public String memberList() {
		log.info("Run MemberList");
		return "member/memberList";
	}

	@RequestMapping("/memberCoupon")
	public String memberCoupon() {
		log.info("Run MemberCoupon");
		return "member/memberCoupon";
	}
	
	@RequestMapping("/memberOrder")
	public String memberOrder() {
		log.info("Run MemberOrder");
		return "member/memberOrder";
	}
	
	@RequestMapping("/memberPoint")
	public String memberPoint() {
		log.info("Run memberPoint");
		return "member/memberPoint";
	}
}
