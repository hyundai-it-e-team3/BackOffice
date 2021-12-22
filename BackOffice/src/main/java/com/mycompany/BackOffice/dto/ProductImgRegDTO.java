package com.mycompany.BackOffice.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductImgRegDTO {
	private String img;
	private String productDetailId;
	private String orderNum;
}
