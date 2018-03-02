package com.rhb.joojoo.api.question;

public class WrongDTO {
	private String image;			//错题图片
	private String imageUrl;			//错题图片链接
	private String madeDate;
	private String tag; //错误原因标签（审题、计算、答非所问、不会）
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(String madeDate) {
		this.madeDate = madeDate;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	
}
