package com.mycompany.BackOffice.service;

import java.util.List;

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
	

	
	public List<SaleDashboard> getQurterSales() {
		Mono<List<SaleDashboard>> response = client
							.get()
							.uri("/dashboard/quater")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<SaleDashboard>>() {});
		return response.block();
	}
	
	public List<SaleDashboard> getDailySales() {
		Mono<List<SaleDashboard>> response = client
							.get()
							.uri("/dashboard/daily")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<SaleDashboard>>() {});
		return response.block();
	}
	
	public List<SaleDashboard> getBrandSales() {
		Mono<List<SaleDashboard>> response = client
							.get()
							.uri("/dashboard/brand")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<SaleDashboard>>() {});
		return response.block();
	}
	
	public List<MemberDashboard> getMemberInfo() {
		Mono<List<MemberDashboard>> response = client
							.get()
							.uri("/dashboard/brand")
							.retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<MemberDashboard>>() {});
		return response.block();
	}
	

}
