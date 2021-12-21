package com.mycompany.BackOffice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.member.Coupon;
import com.mycompany.BackOffice.dto.member.Event;
import com.mycompany.BackOffice.dto.member.PagerAndCoupon;
import com.mycompany.BackOffice.dto.member.PagerAndEvent;
import com.mycompany.BackOffice.dto.member.SearchTypeCoupon;
import com.mycompany.BackOffice.dto.member.SearchTypeEvent;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/promotion")
public class PromotionController {

	private WebClient webClient = WebClient.builder().baseUrl("http://localhost:83")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

	//====================================이벤트===================================	
	@RequestMapping("/firstEventList")
	public String firstEventList(@RequestParam(defaultValue = "1") int pageNo, Model model) {
		log.info("Run FirstEventList");

		SearchTypeEvent searchTypeEvent = new SearchTypeEvent();
		searchTypeEvent.setSearchName("none");
		searchTypeEvent.setSearchStartDate("none");
		searchTypeEvent.setSearchLastDate("none");
		searchTypeEvent.setSearchType(' ');
		searchTypeEvent.setSearchStatus(' ');

		PagerAndEvent data = webClient
				.post()
				.uri("/event/list" + "?pageNo=" + pageNo)
				.body(BodyInserters.fromValue(searchTypeEvent))
				.retrieve()
				.bodyToMono(PagerAndEvent.class)
				.block();

		model.addAttribute("pager", data.getPager());
		model.addAttribute("eventList", data.getEvent());

		return "promotion/eventList";
	}

	@RequestMapping("/eventList")
	public String eventList(@RequestParam(defaultValue = "1") int pageNo,
			@RequestBody(required = false) SearchTypeEvent searchTypeEvent, Model model) {
		log.info("Run EventList");

		if (searchTypeEvent.getSearchName().equals("")) {
			searchTypeEvent.setSearchName("none");
		}
		if (searchTypeEvent.getSearchStartDate().equals("")) {
			searchTypeEvent.setSearchStartDate("none");
		}
		if (searchTypeEvent.getSearchLastDate().equals("")) {
			searchTypeEvent.setSearchLastDate("none");
		}
		if (searchTypeEvent.getSearchType() == 0) {
			searchTypeEvent.setSearchType(' ');
		}
		if (searchTypeEvent.getSearchStatus() == 0) {
			searchTypeEvent.setSearchStatus(' ');
		}
		
		log.info(searchTypeEvent.toString());
		
		PagerAndEvent data = webClient
				.post()
				.uri("/event/list" + "?pageNo=" + pageNo)
				.body(BodyInserters.fromValue(searchTypeEvent))
				.retrieve()
				.bodyToMono(PagerAndEvent.class)
				.block();

		model.addAttribute("pager", data.getPager());
		model.addAttribute("eventList", data.getEvent());

		return "/promotion/eventListFragment";
	}

	@RequestMapping("/registerWithEventForm")
	public String registerWithEventForm() {
		log.info("Run RegisterWithEventForm");
		return "/promotion/registerWithEvent";
	}

	@RequestMapping("/registerWithEvent")
	@ResponseBody
	public Map<String, String> registerWithEvent(Event event) {
		log.info("Run RegisterWithEvent");
		log.info(event.toString());
		
		String response = webClient
				.post()
				.uri("/event")
				.bodyValue(event)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		log.info(response);
		log.info(map.toString());
		
		return map;
	}
	
	@RequestMapping("/editEventForm/{eventSeq}")
	public String editEventForm(@PathVariable int eventSeq, Model model) {
		log.info("Run EditEvent");
		
		Event response = webClient
				.get()
				.uri("/event/" + eventSeq)
				.retrieve()
				.bodyToMono(Event.class)
				.block();
		
		model.addAttribute("event", response);
		
		return "promotion/editEvent";
	}
	
	@RequestMapping("/editEvent")
	@ResponseBody
	public Map<String, String> editEvent(Event event) {
		log.info("Run EditEvent");
		
		String response = webClient
				.post()
				.uri("/event/update")
				.bodyValue(event)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		
		return map;
	}
	
	@RequestMapping("/event/delete")
	@ResponseBody
	public Map<String, String> deleteEvent(@RequestBody List<String> eventSeq) {
		log.info("Run DeleteEvent");
		
		String response = webClient
				.post()
				.uri("/event/delete")
				.bodyValue(eventSeq)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		log.info(response);
		
		return map;
	}

	//====================================쿠폰===================================
	@RequestMapping("/firstCouponList")
	public String firstCouponList(@RequestParam(defaultValue = "1") int pageNo, Model model) {
		log.info("Run FirstCouponList");

		SearchTypeCoupon searchTypeCoupon = new SearchTypeCoupon();
		searchTypeCoupon.setSearchName("none");
		searchTypeCoupon.setSearchStatus(' ');

		PagerAndCoupon data = webClient.post().uri("/coupon/list" + "?pageNo=" + pageNo)
				.body(BodyInserters.fromValue(searchTypeCoupon)).retrieve().bodyToMono(PagerAndCoupon.class).block();

		model.addAttribute("pager", data.getPager());
		model.addAttribute("couponList", data.getCoupon());

		return "promotion/couponList";
	}

	@PostMapping("/couponList")
	public String couponList(@RequestParam(defaultValue = "1") int pageNo,
			@RequestBody(required = false) SearchTypeCoupon searchTypeCoupon, Model model) {
		log.info("Run CouponList");

		if (searchTypeCoupon.getSearchName().equals("")) {
			searchTypeCoupon.setSearchName("none");
		}
		if (searchTypeCoupon.getSearchStatus() == 0) {
			searchTypeCoupon.setSearchStatus(' ');
		}

		PagerAndCoupon data = webClient
				.post()
				.uri("/coupon/list" + "?pageNo=" + pageNo)
				.body(BodyInserters.fromValue(searchTypeCoupon))
				.retrieve()
				.bodyToMono(PagerAndCoupon.class).block();

		model.addAttribute("pager", data.getPager());
		model.addAttribute("couponList", data.getCoupon());

		return "promotion/couponListFragment";
	}

	@RequestMapping("/firstCouponListModal")
	public String firstCouponListModal(@RequestParam(defaultValue = "1") int pageNo, Model model) {
		log.info("Run FirstCouponListModal");

		SearchTypeCoupon searchTypeCoupon = new SearchTypeCoupon();
		searchTypeCoupon.setSearchName("none");
		searchTypeCoupon.setSearchStatus(' ');

		PagerAndCoupon data = webClient
				.post()
				.uri("/coupon/list" + "?pageNo=" + pageNo)
				.body(BodyInserters.fromValue(searchTypeCoupon))
				.retrieve()
				.bodyToMono(PagerAndCoupon.class)
				.block();

		model.addAttribute("pager", data.getPager());
		model.addAttribute("couponList", data.getCoupon());

		return "promotion/couponListModal";
	}

	@PostMapping("/couponListModal")
	public String couponListModal(@RequestParam(defaultValue = "1") int pageNo,
			@RequestBody(required = false) SearchTypeCoupon searchTypeCoupon, Model model) {
		log.info("Run CouponListModal");

		if (searchTypeCoupon.getSearchName().equals("")) {
			searchTypeCoupon.setSearchName("none");
		}
		if (searchTypeCoupon.getSearchStatus() == 0) {
			searchTypeCoupon.setSearchStatus(' ');
		}

		PagerAndCoupon data = webClient
				.post()
				.uri("/coupon/list" + "?pageNo=" + pageNo)
				.body(BodyInserters.fromValue(searchTypeCoupon))
				.retrieve()
				.bodyToMono(PagerAndCoupon.class)
				.block();

		model.addAttribute("pager", data.getPager());
		model.addAttribute("couponList", data.getCoupon());

		return "promotion/couponListFragment";
	}

	@RequestMapping("/registerWithCouponForm")
	public String registerWithCouponForm() {
		log.info("Run registerWithCouponForm");
		return "promotion/registerWithCoupon";
	}

	@RequestMapping("/registerWithCoupon")
	@ResponseBody
	public Map<String, String> registerWithEventCoupon(Coupon coupon) {
		log.info("Run RegisterWithEventCoupon");

		String response = webClient
				.post()
				.uri("/coupon")
				.bodyValue(coupon)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		map.put("couponId", coupon.getCouponId());

		return map;
	}

	@RequestMapping("/editCouponForm/{couponId}")
	public String editCouponForm(@PathVariable String couponId, Model model) {
		log.info("Run EditCouponForm");
		
		Coupon response = webClient
				.get()
				.uri("/coupon/" + couponId)
				.retrieve()
				.bodyToMono(Coupon.class)
				.block();
		
		model.addAttribute("coupon", response);
		
		return "promotion/editCoupon";
	}

	@RequestMapping("/editCoupon")
	@ResponseBody
	public Map<String, String> editCoupon(Coupon coupon) {
		log.info("Run EditCoupon");
		
		String response = webClient
				.post()
				.uri("/coupon/update")
				.bodyValue(coupon)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		
		return map;
	}
	
	@RequestMapping("/coupon/delete")
	@ResponseBody
	public Map<String, String> deleteCoupon(@RequestBody List<String> couponId) {
		log.info("Run DeleteCoupon");
		
		String response = webClient
				.post()
				.uri("/coupon/delete")
				.bodyValue(couponId)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		Map<String, String> map = new HashMap<>();
		map.put("result", response);
		log.info(response);
		
		return map;
	}
}
