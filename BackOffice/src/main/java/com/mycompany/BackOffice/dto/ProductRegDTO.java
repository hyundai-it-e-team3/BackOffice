package com.mycompany.BackOffice.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductRegDTO {
	private String productId;
	private String name;
	private int price;
	private String content;
	private int hitCount;
	private int status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regDate;
	private String brandName;
	private String thumbnail;
	private String categoryId;
	private String parentCategoryId;
	private int clevel;
	private String categoryName;
	private int mdStatus;
	private List<ProductDetailRegDTO> productDetailList;
	private List<String> categoryList;
}
