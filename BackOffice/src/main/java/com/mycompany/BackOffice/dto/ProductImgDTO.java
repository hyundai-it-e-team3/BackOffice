package com.mycompany.BackOffice.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductImgDTO {
	private MultipartFile imgFile;
	private String img;
	private String productDetailId;
	private String orderNum;
}
