package com.mycompany.BackOffice.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDTO {
	private String categoryId;
	private String parentCategoryId;
	private String name;
	private int clevel;
	private List<CategoryDTO> categoryList;
}


