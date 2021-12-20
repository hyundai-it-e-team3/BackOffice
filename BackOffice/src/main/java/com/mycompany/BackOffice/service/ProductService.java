package com.mycompany.BackOffice.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.BrandCategoryDTO;
import com.mycompany.BackOffice.dto.BrandDTO;
import com.mycompany.BackOffice.dto.CategoryDTO;
import com.mycompany.BackOffice.dto.ProductDTO;
import com.mycompany.BackOffice.dto.ProductSearchDTO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductService {
	
	private WebClient client = WebClient
								.builder()
								.baseUrl("http://localhost:83")
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
	
	public List<BrandCategoryDTO> getBrand() {
		Mono<List<BrandCategoryDTO>> response = client
									.get()
									.uri("/category/brand")
									.retrieve()
									.bodyToMono(new ParameterizedTypeReference<List<BrandCategoryDTO>>() {});
		
		return response.block();
	}
	
	
	public Map<String,String> regProduct(ProductDTO productDTO){
        return client.post()         // POST method
                .uri("/product/regist")    // baseUrl 이후 uri
                .bodyValue(productDTO)     // set body value
                .retrieve()                 // client message 전송
                .bodyToMono(Map.class)  // body type : EmpInfo
                .block();                   // await
        
	}

	public ProductDTO getProduct(String productId) {
		Mono<ProductDTO> response = client
							.get()
							.uri("/product/"+productId)
							.retrieve()
							.bodyToMono(ProductDTO.class);
		return response.block();
	}

	public Map<String,Object> getProductList(ProductSearchDTO productSearchDTO) {
		// TODO Auto-generated method stub
        return client.post()         // POST method
                .uri("/product/productList")    // baseUrl 이후 uri
                .bodyValue(productSearchDTO)     // set body value
                .retrieve()                 // client message 전송
                .bodyToMono(Map.class)  // body type : EmpInfo
                .block();   
	}

	public Map<String, Object> getProductDetailList(Map<String, String> map) {
        return client.post()         // POST method
                .uri("/product/stockList")    // baseUrl 이후 uri
                .bodyValue(map)     // set body value
                .retrieve()                 // client message 전송
                .bodyToMono(Map.class)  // body type : EmpInfo
                .block();  
	}

	public Map<String, Object> withProductList(String productDetailId,String pageNo) {
        return client.get()         // POST method
                .uri("/product/withProductList?productDetailId="+productDetailId+"&pageNo="+pageNo)    // baseUrl 이후 uri
                .retrieve()                 // client message 전송
                .bodyToMono(Map.class)  // body type : EmpInfo
                .block(); 
	}

	public List<String> getAllSize() {
		return client.get()         // POST method
                .uri("/product/sizeList")    // baseUrl 이후 uri
                .retrieve()                 // client message 전송
                .bodyToMono(new ParameterizedTypeReference<List<String> >() {})  // body type : EmpInfo
                .block(); 
	}

}
