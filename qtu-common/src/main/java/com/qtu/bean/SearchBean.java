package com.qtu.bean;

import lombok.Data;

@Data
public class SearchBean {
	private String keywords;
	private String category;
	private String brand;
	private String price;
	private Integer pageNo;
	private Integer pageSize;
	private String sortField;
	private String sort;
}
