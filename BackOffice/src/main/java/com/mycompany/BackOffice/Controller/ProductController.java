package com.mycompany.BackOffice.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.BackOffice.dto.CategoryDTO;
import com.mycompany.BackOffice.dto.ProductDTO;
import com.mycompany.BackOffice.service.ProductService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/product")
public class ProductController {
	
	@Resource 
	private ProductService productService;
	
	@RequestMapping("/list")
	public String productList(Model model) {
		log.info("실행");
		List<CategoryDTO> categoryList = productService.getCategory();
		log.info(categoryList.toString());
		model.addAttribute("categoryList", categoryList);
		return "/product/productList";
	}
	@RequestMapping("/register")
	public String productRegister(Model model) {
		log.info("실행");
		List<CategoryDTO> categoryList = productService.getCategory();
		log.info(categoryList.toString());
		model.addAttribute("categoryList", categoryList);
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
