package com.mycompany.BackOffice.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.order.OrderInfo;

import reactor.core.publisher.Mono;

@Service
public class OrderService {	
	private WebClient client = WebClient
			.builder()
			.baseUrl("http://localhost:83")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	
	public List<OrderInfo> getOrderInfoList() {
		Mono<List<OrderInfo>> response = client
							.get()
							.uri("/order/infolist")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<OrderInfo>>() {});
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

}
