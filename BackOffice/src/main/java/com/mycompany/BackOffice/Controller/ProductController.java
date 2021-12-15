package com.mycompany.BackOffice.Controller;

import java.util.List;import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.BackOffice.dto.BrandCategoryDTO;
import com.mycompany.BackOffice.dto.BrandDTO;
import com.mycompany.BackOffice.dto.CategoryDTO;
import com.mycompany.BackOffice.dto.ColorDTO;
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
		model.addAttribute("categoryList", categoryList);
		return "/product/productList";
	}
	
	
	@PostMapping("/regist")
	@ResponseBody
	public Map<String,String> productRegist(@RequestBody ProductDTO productDTO) {
		Map<String,String> map= productService.regProduct(productDTO);
		return map;
	}
	
	@RequestMapping("/register")
	public String productRegister(Model model) {
		log.info("실행");
		List<BrandCategoryDTO> brandList = productService.getBrand();
		log.info(brandList.toString());
		model.addAttribute("brandList", brandList);

		return "/product/register";
	}
	@RequestMapping("/detail")
	public String productDetail() {
		log.info("실행");
		return "/product/detail";
	}
	@RequestMapping("/detailModal")
	public String productDetailModal(@RequestParam String productId, Model model) {
		log.info("실행");
		ProductDTO productDTO = productService.getProduct(productId);
		model.addAttribute("product", productDTO);
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
