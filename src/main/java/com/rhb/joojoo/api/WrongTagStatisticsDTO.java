package com.rhb.joojoo.api;

public class WrongTagStatisticsDTO {
	private String name;
	private Integer value;
	
	public WrongTagStatisticsDTO(String name, Integer value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
