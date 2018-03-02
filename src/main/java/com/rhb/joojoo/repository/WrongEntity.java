package com.rhb.joojoo.repository;

public class WrongEntity {
	private String wrongImage;			//错题图片
	private String madeDate;
	private String wrongTag; //错误原因标签（审题、计算、答非所问、不会）
	
	public String getWrongImage() {
		return wrongImage;
	}
	public void setWrongImage(String wrongImage) {
		this.wrongImage = wrongImage;
	}
	public String getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(String madeDate) {
		this.madeDate = madeDate;
	}
	public String getWrongTag() {
		return wrongTag;
	}
	public void setWrongTag(String wrongTag) {
		this.wrongTag = wrongTag;
	}
	
	
	
}
