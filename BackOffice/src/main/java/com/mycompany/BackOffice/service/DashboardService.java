package com.mycompany.BackOffice.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.dashboard.MemberDashboard;
import com.mycompany.BackOffice.dto.dashboard.SaleDashboard;

import reactor.core.publisher.Mono;

@Service
public class DashboardService {
	private WebClient client = WebClient
			.builder()
			.baseUrl("http://localhost:83")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	

	
	public Map<String, Object> getQurterSales() {
		Mono<Map<String, Object>> response = client
							.get()
							.uri("/dashboard/quater")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		return response.block();
	}
	
	public Map<String, Object> getDailySales() {
		Mono<Map<String, Object>> response = client
							.get()
							.uri("/dashboard/daily")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		return response.block();
	}
	
	public Map<String, Object> getBrandSales() {
		Mono<Map<String, Object>> response = client
							.get()
							.uri("/dashboard/brand")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		return response.block();
	}
	
	public Map<String, Object> getMemberInfo() {
		Mono<Map<String, Object>> response = client
							.get()
							.uri("/dashboard/member")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
		return response.block();
	}
	

}
