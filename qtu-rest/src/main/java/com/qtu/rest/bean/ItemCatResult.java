package com.qtu.rest.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemCatResult {

	@JsonProperty("data")
	private List<ItemCatData> data = new ArrayList<>();

	public List<ItemCatData> getData() {
		return data;
	}

	public void setData(List<ItemCatData> data) {
		this.data = data;
	}
	
	
}
