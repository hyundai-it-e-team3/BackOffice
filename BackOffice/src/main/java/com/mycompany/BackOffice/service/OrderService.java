package com.mycompany.BackOffice.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.order.OrderDetail;
import com.mycompany.BackOffice.dto.order.OrderInfo;
import com.mycompany.BackOffice.dto.order.PagerAndOrderInfo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrderService {	
	private WebClient client = WebClient
			.builder()
			.baseUrl("http://localhost:83")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	
	public PagerAndOrderInfo getOrderInfoList(int pageNo) {
		Mono<PagerAndOrderInfo> response = client
							.get()
							.uri("/order/infolist?pageNo="+pageNo)
							.retrieve()
							.bodyToMono(PagerAndOrderInfo.class);
		return response.block();
	}
	
	public Map<String, Object> getOrderInfo(String oid) {
		Mono<Map<String, Object>> response = client
							.get()
							.uri("/order/"+oid)
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		return response.block();
	}
	
	public void updateOdState(OrderDetail orderDetail) {
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("orderId", orderDetail.getOrderId());
		map.add("productDetailId", orderDetail.getProductDetailId());
		map.add("psize", orderDetail.getPsize());
		map.add("stateCode", orderDetail.getStateCode()+"");
		log.info(map.toString());
		Mono<Void> response = client
			.post()
			.uri("/order/detail")
			.body(BodyInserters.fromFormData(map))
			.retrieve().bodyToMono(Void.class);
		
		response.block();
	}
	
	
	public void returnOrder(String orderId) {
		log.info(orderId);
		Mono<Map<String, Object>> response = client
			.put()
			.uri("/order/"+orderId)
			.retrieve()							
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		
		response.block();
	}

}
