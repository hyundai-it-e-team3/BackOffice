package com.mycompany.BackOffice.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.BackOffice.dto.BrandCategoryDTO;
import com.mycompany.BackOffice.dto.BrandDTO;
import com.mycompany.BackOffice.dto.CategoryDTO;
import com.mycompany.BackOffice.dto.ProductDTO;
import com.mycompany.BackOffice.dto.ProductDetailDTO;
import com.mycompany.BackOffice.dto.ProductDetailRegDTO;
import com.mycompany.BackOffice.dto.ProductImgDTO;
import com.mycompany.BackOffice.dto.ProductImgRegDTO;
import com.mycompany.BackOffice.dto.ProductRegDTO;
import com.mycompany.BackOffice.service.ProductService;
import com.mycompany.BackOffice.service.S3UploaderService;
import com.mycompany.BackOffice.dto.ProductSearchDTO;
import com.mycompany.BackOffice.dto.StockDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/product")
public class ProductController {
	
	@Resource 
	private ProductService productService;
	
	@Resource
	private S3UploaderService s3UploaderService;
	
	
	@RequestMapping("/list")
	public String categoryList(Model model) {
		log.info("실행");
		List<CategoryDTO> categoryList = productService.getCategory();
		model.addAttribute("categoryList", categoryList);
		return "/product/productList";
	}
	
	
	@RequestMapping("/productList")
	public String productList(@RequestParam Map<String,String> map,Model model) {
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
		
		
		
		Map<String,Object> resultMap = productService.getProductList(productSearchDTO);
		log.info(resultMap.get("productList").toString());
		log.info(resultMap.get("pager").toString());
		
		
		
		List<ProductDTO> productList = (List<ProductDTO>) resultMap.get("productList");
		
		
		model.addAttribute("productList", productList);
		model.addAttribute("pager", resultMap.get("pager"));
		
		return "/product/productListFragment";
	}
	
	@RequestMapping("/stockList")
	public String stockList(@RequestParam String searchType, @RequestParam String keyWord, @RequestParam String sortId,@RequestParam String pageNo,Model model) {
		log.info("실행");
		Map<String,String> map = new HashMap<>();
		map.put("searchType",searchType);
		map.put("keyWord",keyWord);
		map.put("sortId",sortId);
		map.put("pageNo",pageNo);
		Map<String,Object> resultMap = productService.getProductDetailList(map);

		model.addAttribute("pager", resultMap.get("pager"));
		model.addAttribute("stockList", resultMap.get("stockList"));
		return "/product/stockListFragment";
	}
	
	@PostMapping("/regist")
	@ResponseBody
	public Map<String,String> productRegist(ProductDTO productDTO) throws FileNotFoundException, IOException {
		log.info(productDTO.toString());
		
		ProductRegDTO proRegDTO = new ProductRegDTO();
		proRegDTO.setProductId(productDTO.getProductId());
		proRegDTO.setName(productDTO.getName());
		proRegDTO.setPrice(productDTO.getPrice());
		proRegDTO.setStatus(productDTO.getStatus());
		proRegDTO.setBrandName(productDTO.getBrandName());
		proRegDTO.setCategoryId(productDTO.getCategoryId());
		proRegDTO.setContent(productDTO.getContent());
		//proRegDTO.setCategoryName(productDTO.getCategoryName());
		
		proRegDTO.setThumbnail(s3UploaderService.upload(productDTO.getThumbnailFile(), productDTO.getProductId()+"/thumbnail")); 
		
		proRegDTO.setProductDetailList(new ArrayList<>());
		List<ProductDetailRegDTO> regDetailList = new ArrayList<>();
		for(ProductDetailDTO productDetailDTO: productDTO.getProductDetailList()) {
			ProductDetailRegDTO detailRegDTO = new ProductDetailRegDTO();
			detailRegDTO.setProductDetailId(productDetailDTO.getProductDetailId());
			detailRegDTO.setWithProduct(productDetailDTO.getWithProduct());
			detailRegDTO.setColorCode(productDetailDTO.getColorCode());
			detailRegDTO.setProductId(proRegDTO.getProductId());
			detailRegDTO.setImgList(new ArrayList<>());
			detailRegDTO.setStockList(new ArrayList<>());
			for(ProductImgDTO productImgDTO : productDetailDTO.getImgList()) {
				ProductImgRegDTO imgRegDTO = new ProductImgRegDTO();
				imgRegDTO.setImg(s3UploaderService.upload(productImgDTO.getImgFile(), productDTO.getProductId()+"/"+productDetailDTO.getProductDetailId()));
				imgRegDTO.setProductDetailId(productImgDTO.getProductDetailId());
				imgRegDTO.setOrderNum(productImgDTO.getOrderNum());
				detailRegDTO.getImgList().add(imgRegDTO);
			}
			for(StockDTO stockDTO : productDetailDTO.getStockList()) {
				StockDTO stock = new StockDTO();
				stock.setAmount(stockDTO.getAmount());
				stock.setPsize(stockDTO.getPsize());
				stock.setProductDetailId(detailRegDTO.getProductDetailId());
				detailRegDTO.getStockList().add(stock);
			}
			detailRegDTO.setColorChip(s3UploaderService.upload(productDetailDTO.getColorChipFile(), productDTO.getProductId()+"/"+productDetailDTO.getProductDetailId()+"/colorChip"));
			proRegDTO.getProductDetailList().add(detailRegDTO);
		}
		log.info(proRegDTO.toString());
		Map<String,String> map = productService.regProduct(proRegDTO);
		return map;
	}
	
	@RequestMapping("/register")
	public String productRegister(Model model) {
		log.info("실행");
		List<BrandCategoryDTO> brandList = productService.getBrand();
		List<String> sizeList = productService.getAllSize();
		log.info(brandList.toString());
		model.addAttribute("brandList", brandList);
		model.addAttribute("sizeList", sizeList);
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
	
	
	@RequestMapping("/withProductList")
	public String withProductListModal(@RequestParam String productDetailId,@RequestParam String pageNo, Model model) {
		log.info("실행");
		Map<String,Object> resultMap = productService.withProductList(productDetailId,pageNo);
		
		
		log.info(resultMap.get("productDetailList").toString());
		
		model.addAttribute("productDetailList", resultMap.get("productDetailList"));
		model.addAttribute("pager", resultMap.get("pager"));
		
		return "/product/withProductListFragment";
	}
	
	@RequestMapping("/withProductModal")
	public String withProductListModal(@RequestParam String productDetailId,Model model) {
		log.info("실행");		
		model.addAttribute("productDetailId", productDetailId);
		return "/product/withProductListModal";
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
		productSearchDTO.setMdStatus(Integer.parseInt(map.get("mdStatus")));
		
		Map<String,Object> resultMap = productService.getProductList(productSearchDTO);
		log.info(resultMap.get("productList").toString());
		log.info(resultMap.get("pager").toString());
		
		
		
		List<ProductDTO> productList = (List<ProductDTO>) resultMap.get("productList");
		
		
		model.addAttribute("productList", productList);
		model.addAttribute("pager", resultMap.get("pager"));
		
		return "/product/brandProductFragment";
	}
	
	
	@PostMapping("/changeStatus")
	@ResponseBody
	public String  changeStatus(@RequestBody List<String> productIdList) {
		productService.changeStatus(productIdList);
		return "success";
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
