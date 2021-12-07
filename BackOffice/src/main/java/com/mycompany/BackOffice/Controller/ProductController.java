package com.mycompany.BackOffice.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/product")
public class ProductController {
	
	
	
	@RequestMapping("/list")
	public String productList() {
		log.info("실행");
		return "/product/productList";
	}
	@RequestMapping("/register")
	public String productRegister() {
		log.info("실행");
		return "/product/register";
	}
	@RequestMapping("/detail")
	public String productDetail() {
		log.info("실행");
		return "/product/detail";
	}
	@RequestMapping("/detailModal")
	public String productDetailModal() {
		log.info("실행");
		return "/product/detailModal";
	}
	@RequestMapping("/update")
	public String productUpdate() {
		log.info("실행");
		return "/product/update";
	}
	@RequestMapping("/stock")
	public String productStock() {
		log.info("실행");
		return "/product/stock";
	}
}
