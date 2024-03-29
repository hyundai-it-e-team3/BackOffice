package com.mycompany.BackOffice.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDetailDTO {
	private String productDetailId;
	private String withProduct;
	private String colorCode;
	private String colorChip;
	private MultipartFile colorChipFile;
	private String productId;
	private String name;
	private String brandName;
	private List<ProductImgDTO> imgList;
	private List<ProductImgDTO> withImgList;
	private List<StockDTO> stockList;
}
