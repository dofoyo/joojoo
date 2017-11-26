package com.rhb.joojoo.api;

public class QuestionDTO {
	private String id;
	private String originalImage;  			// 原题图片
	private String content;					// 题目
	private String contentImage;  			// 题目图片
	private String originalImageUrl;
	private String contentImageUrl;
	private Integer rightTimes = 0;					//正确次数
	private Integer wrongTimes = 1;					//错误次数
	
	public Double getWrongRate(){
		return new Double(wrongTimes/(rightTimes + wrongTimes));
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOriginalImage() {
		return originalImage;
	}
	public void setOriginalImage(String originalImage) {
		this.originalImage = originalImage;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentImage() {
		return contentImage;
	}
	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}
	public String getOriginalImageUrl() {
		return originalImageUrl;
	}
	public void setOriginalImageUrl(String originalImageUrl) {
		this.originalImageUrl = originalImageUrl;
	}
	public String getContentImageUrl() {
		return contentImageUrl;
	}
	public void setContentImageUrl(String contentImageUrl) {
		this.contentImageUrl = contentImageUrl;
	}
	public Integer getRightTimes() {
		return rightTimes;
	}
	public void setRightTimes(int rightTimes) {
		this.rightTimes = rightTimes;
	}
	public Integer getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(int wrongTimes) {
		this.wrongTimes = wrongTimes;
	}


}
