package com.mycompany.BackOffice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.BackOffice.dto.member.Manager;
import com.mycompany.BackOffice.security.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	
	private WebClient webClient = WebClient
			.builder()
			.baseUrl("http://localhost:83")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("실행");
		
		Manager manager = webClient
				.get()
				.uri("/manager/" + username)
				.retrieve()
				.bodyToMono(Manager.class)
				.block();
				
		if(manager == null) {
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(manager.getManagerRole()));
		
		CustomUserDetails userDetails = new CustomUserDetails(manager.getId(), 
															  manager.getPassword(), 
															  authorities);
		
		return userDetails;
	}
	
}
