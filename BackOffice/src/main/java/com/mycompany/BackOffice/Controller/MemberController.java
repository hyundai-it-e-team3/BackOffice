package com.mycompany.BackOffice.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.member.Member;
import com.mycompany.BackOffice.dto.member.PagerAndMember;
import com.mycompany.BackOffice.dto.member.PagerAndMemberCoupon;
import com.mycompany.BackOffice.dto.member.PagerAndPoint;
import com.mycompany.BackOffice.dto.member.SearchTypeMember;
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
	
	@RequestMapping("/firstMemberList")
	public String firstMemberList(@RequestParam(defaultValue="1") int pageNo, Model model) {
		log.info("Run FirstMemberList");

		SearchTypeMember searchTypeMember = new SearchTypeMember();
		searchTypeMember.setSearchMemberId("none");
		searchTypeMember.setSearchName("none");
		searchTypeMember.setMemberLevelList(new ArrayList<String>());
		
		PagerAndMember data = webClient
				.post()
			    .uri("/member/" + "?pageNo=" + pageNo)
			    .body(BodyInserters.fromValue(searchTypeMember))
			    .retrieve()
			    .bodyToMono(PagerAndMember.class)
			    .block();
		
		model.addAttribute("pager", data.getPager());
		model.addAttribute("memberList", data.getMember());
		
		return "member/memberList";
	}
	
	@RequestMapping("/memberList")
	public String memberList(@RequestParam(defaultValue="1") int pageNo, 
							 @RequestBody(required=false) SearchTypeMember searchTypeMember,
							 Model model) {
		log.info("Run MemberList");
		
		
		if(searchTypeMember.getSearchMemberId().equals("")) {
			searchTypeMember.setSearchMemberId("none");
		} 
		if(searchTypeMember.getSearchName().equals("")) {
			searchTypeMember.setSearchName("none");
		}

		PagerAndMember data = webClient
				.post()
			    .uri("/member" + "?pageNo=" + pageNo)
			    .body(BodyInserters.fromValue(searchTypeMember))
			    .retrieve()
			    .bodyToMono(PagerAndMember.class)
			    .block();
		
		model.addAttribute("pager", data.getPager());
		model.addAttribute("memberList", data.getMember());
		
		return "member/memberListFragment";
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
	
	@RequestMapping("/updateMemberForm/{memberId}")
	public String updateMemberForm(@PathVariable String memberId, Model model) {
		log.info("Run UpdateMemberForm");
		
		Member member = webClient
				.get()
				.uri("/member/" + memberId)
				.retrieve()
				.bodyToMono(Member.class)
				.block();
		
		model.addAttribute("member", member);
		
		return "member/updateMemberForm";
	}
	
	@RequestMapping("/updateMember")
	@ResponseBody
	public Map<String, String> updateMember(Member member) {
		log.info("Run UpdateMember");
		
		String response = webClient
			.post()
			.uri("/member/update")
			.bodyValue(member)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		
		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		
		return map;
	}
}
