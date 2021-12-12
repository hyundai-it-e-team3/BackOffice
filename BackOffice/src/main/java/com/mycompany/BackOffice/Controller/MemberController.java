package com.mycompany.BackOffice.Controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.member.Member;
import com.mycompany.BackOffice.dto.member.MemberCoupon;
import com.mycompany.BackOffice.dto.member.Point;
import com.mycompany.BackOffice.dto.order.OrderInfo;

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
	public String memberList(Model model) {
		log.info("Run MemberList");
		
		List<Member> memberList = webClient
									.get()
								    .uri("/member")
								    .retrieve()
								    .bodyToMono(new ParameterizedTypeReference<List<Member>>() {})
								    .block();
		
		model.addAttribute(memberList);
		
		return "member/memberList";
	}

	@RequestMapping("/memberCoupon/{memberId}")
	public String memberCoupon(@PathVariable String memberId, @RequestParam String name, Model model) {
		log.info("Run MemberCoupon");
		
		List<MemberCoupon> couponList = webClient
				.get()
				.uri("/member/coupon/list/" + memberId)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<MemberCoupon>>() {})
				.block();

		model.addAttribute("couponList", couponList);
		model.addAttribute("name", name);
		log.info(couponList.toString());
		
		return "member/memberCoupon";
	}
	
	@RequestMapping("/memberOrder/{memberId}")
	public String memberOrder(@PathVariable String memberId, Model model) {
		log.info("Run MemberOrder");
		
		List<OrderInfo> orderList = webClient
				.get()
				.uri("/order/infolist/" + memberId)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<OrderInfo>>() {})
				.block();
		
		model.addAttribute("orderList", orderList);
		
		return "member/memberOrder";
	}
	
	@RequestMapping("/memberPoint/{memberId}")
	public String memberPoint(@PathVariable String memberId, @RequestParam String name, Model model) {
		log.info("Run memberPoint");
		
		List<Point> pointList = webClient
				.get()
				.uri("/point/list/" + memberId)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Point>>() {})
				.block();

		model.addAttribute("pointList", pointList);
		model.addAttribute("name", name);
	
		return "member/memberPoint";
	}
}
