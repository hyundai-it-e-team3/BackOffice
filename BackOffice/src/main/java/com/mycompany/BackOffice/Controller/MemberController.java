package com.mycompany.BackOffice.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.member.PagerAndMember;
import com.mycompany.BackOffice.dto.member.PagerAndMemberCoupon;
import com.mycompany.BackOffice.dto.member.PagerAndPoint;
import com.mycompany.BackOffice.dto.order.PagerAndOrderInfo;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
	
	private WebClient webClient = WebClient
									.builder()
									.baseUrl("http://localhost:83")
									.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
									.build();
	
	@RequestMapping("/memberList")
	public String memberList(@RequestParam(defaultValue="1") int pageNo, Model model) {
		log.info("Run MemberList");
		
		PagerAndMember data = webClient
				.get()
			    .uri("/member")
			    .retrieve()
			    .bodyToMono(PagerAndMember.class)
			    .block();
		
		model.addAttribute("pager", data.getPager());
		model.addAttribute("memberList", data.getMember());
		
		return "member/memberList";
	}

	@RequestMapping("/memberCoupon/{memberId}")
	public String memberCoupon(@PathVariable String memberId, @RequestParam(defaultValue="1") int pageNo, @RequestParam String name, Model model) {
		log.info("Run MemberCoupon");
		
		PagerAndMemberCoupon data = webClient
				.get()
				.uri("/member/coupon/list/" + memberId + "?pageNo=" + pageNo)
				.retrieve()
				.bodyToMono(PagerAndMemberCoupon.class)
				.block();

		model.addAttribute("couponList", data.getMemberCoupon());
		model.addAttribute("name", name);
		model.addAttribute("pager", data.getPager());
		
		return "member/memberCoupon";
	}
	
	@RequestMapping("/memberOrder/{memberId}")
	public String memberOrder(@PathVariable String memberId, @RequestParam(defaultValue="1") int pageNo, @RequestParam String name, Model model) {
		log.info("Run MemberOrder");
		
		PagerAndOrderInfo data = webClient
				.get()
				.uri("/order/infolist/" + memberId + "?pageNo=" + pageNo)
				.retrieve()
				.bodyToMono(PagerAndOrderInfo.class)
				.block();
		
		model.addAttribute("orderList", data.getOrderInfos());
		model.addAttribute("name", name);
		model.addAttribute("pager", data.getPager());
		
		return "member/memberOrder";
	}
	
	@RequestMapping("/memberPoint/{memberId}")
	public String memberPoint(@PathVariable String memberId, @RequestParam(defaultValue="1") int pageNo, @RequestParam String name, Model model) {
		log.info("Run memberPoint");
		
		PagerAndPoint data = webClient
				.get()
				.uri("/point/list/" + memberId + "?pageNo=" + pageNo)
				.retrieve()
				.bodyToMono(PagerAndPoint.class)
				.block();

		model.addAttribute("pointList", data.getPoint());
		model.addAttribute("name", name);
		model.addAttribute("pager", data.getPager());
	
		return "member/memberPoint";
	}
}
