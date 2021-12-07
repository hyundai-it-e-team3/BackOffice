package com.mycompany.BackOffice.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.CategoryDTO;
import com.mycompany.BackOffice.dto.ProductDTO;

import reactor.core.publisher.Mono;

@Service
public class ProductService {
	
	private WebClient client = WebClient
								.builder()
								.baseUrl("http://kosa1.iptime.org:50503")
								.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.build();

	public List<CategoryDTO> getCategory() {
		Mono<List<CategoryDTO>> response = client
									.get()
									.uri("/category")
									.retrieve()
									.bodyToMono(new ParameterizedTypeReference<List<CategoryDTO>>() {});
		
		return response.block();
	}
	
	public ProductDTO getProduct() {
		String productId = "CM2B0KJC210W";
		Mono<ProductDTO> response = client
							.get()
							.uri("/product/"+productId)
							.retrieve()
							.bodyToMono(ProductDTO.class);
		return response.block();
	}
}
