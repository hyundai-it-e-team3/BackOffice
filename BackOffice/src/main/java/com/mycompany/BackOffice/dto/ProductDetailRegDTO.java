package com.mycompany.BackOffice.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDetailRegDTO {
	private String productDetailId;
	private String withProduct;
	private String colorCode;
	private String colorChip;
	private String productId;
	private String name;
	private String brandName;
	private List<ProductImgRegDTO> imgList;
	private List<ProductImgRegDTO> withImgList;
	private List<StockDTO> stockList;
}
