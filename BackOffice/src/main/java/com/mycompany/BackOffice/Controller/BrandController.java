package com.mycompany.BackOffice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.BackOffice.dto.BrandCategoryDTO;
import com.mycompany.BackOffice.dto.ProductDTO;
import com.mycompany.BackOffice.dto.ProductSearchDTO;
import com.mycompany.BackOffice.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/brand")
public class BrandController {
	
	@Resource 
	private ProductService productService;
	
	@RequestMapping("/manager")
	public String manager(Model model) {
		List<BrandCategoryDTO> brandList = productService.getBrand();
		log.info(brandList.toString());
		model.addAttribute("brandList", brandList);
		return "brand/manager";
	}
	
	@RequestMapping("/brandProducts")
	public String mdPick(Model model) {
		List<BrandCategoryDTO> brandList = productService.getBrand();
		log.info(brandList.toString());
		model.addAttribute("brandList", brandList);
		return "brand/brandProducts";
	}
	
	@RequestMapping("/brandProductList")
	public String brandProductList(@RequestParam Map<String,String> map,Model model) {
		
		log.info("실행");
		ProductSearchDTO productSearchDTO = new ProductSearchDTO();
		productSearchDTO.setSearchType(map.get("searchType"));
		productSearchDTO.setKeyWord(map.get("keyWord"));
		productSearchDTO.setCategoryId(map.get("categoryId"));
		productSearchDTO.setRegStart(map.get("regStart"));
		productSearchDTO.setRegEnd(map.get("regEnd"));
		productSearchDTO.setStatus(map.get("status"));
		productSearchDTO.setSortId(map.get("sortId"));
		productSearchDTO.setPageNo(map.get("pageNo"));
		productSearchDTO.setBrandName(map.get("brandName"));
		productSearchDTO.setMdStatus(Integer.parseInt(map.get("mdStatus")));
		
		log.info(productSearchDTO.toString());
		
		Map<String,Object> resultMap = productService.getProductList(productSearchDTO);
		log.info(resultMap.get("productList").toString());
		log.info(resultMap.get("pager").toString());
		
		
		
		List<ProductDTO> productList = (List<ProductDTO>) resultMap.get("productList");
		
		
		model.addAttribute("productList", productList);
		model.addAttribute("pager", resultMap.get("pager"));
		
		return "/product/brandProductFragment";
	}
	
	@PostMapping("/regMdProduct")
	@ResponseBody
	public Map<String,String> regMdProduct(@RequestBody ProductDTO productDTO){
		Map<String,String> map = new HashMap<>();
		
		map = productService.regMdProduct(productDTO);
		
		return map;
	}
	
	@PostMapping("/delMdProduct")
	@ResponseBody
	public Map<String,String> delMdProduct(@RequestBody ProductDTO productDTO){
		Map<String,String> map = new HashMap<>();
		
		map = productService.delMdProduct(productDTO);
		
		return map;
	}
}
