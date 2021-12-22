package com.mycompany.BackOffice.dto;

import java.util.List;

import lombok.Data;

@Data
public class BrandCategoryDTO {
	private String brandName;
	private String mainImg;
	private String content;
	private String logoImg;
	private String brandImg;
	private List<BrandCategoryTempDTO> brandCategoryTempList;
	private List<ProductDTO> mdPickList;
}