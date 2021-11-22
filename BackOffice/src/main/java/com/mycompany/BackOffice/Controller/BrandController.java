package com.mycompany.BackOffice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/brand")
public class BrandController {
	
	@RequestMapping("/manager")
	public String manager() {
		return "brand/manager";
	}
	
	@RequestMapping("/brandProducts")
	public String mdPick() {
		return "brand/brandProducts";
	}
}
