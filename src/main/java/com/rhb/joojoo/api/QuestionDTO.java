package com.rhb.joojoo.api;

import java.text.NumberFormat;

public class QuestionDTO {
	private String id;
	private String originalImage;  			// 原题图片
	private String content;					// 题目
	private String contentImage;  			// 题目图片
	private String originalImageUrl;
	private String contentImageUrl;
	private Integer rightTimes = 0;					//正确次数
	private Integer wrongTimes = 1;					//错误次数
	private String knowledgeTag;	//知识点标签
	private Integer difficulty = 0;
	private String wrongTag;	//错误点
	private String[] worngImages; //错题图片
	private String[] worngImageUrls;  
	private String school; 					//学校


	
	
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String[] getWorngImageUrls() {
		return worngImageUrls;
	}

	public void setWorngImageUrls(String[] worngImageUrls) {
		this.worngImageUrls = worngImageUrls;
	}

	public String[] getWorngImages() {
		return worngImages;
	}

	public void setWorngImages(String[] worngImages) {
		this.worngImages = worngImages;
	}

	public String getWrongTag() {
		return wrongTag;
	}

	public void setWrongTag(String wrongTag) {
		this.wrongTag = wrongTag;
	}

	private boolean isInteger(String str){
		try {  
	         Integer.parseInt(str);  
	         return true;  
	     } catch (NumberFormatException e) {  
	         return false;  
	     } 
	}
	
	public boolean isMatchKeyword(String keywordFilter){
		boolean flag = false;
		if(keywordFilter==null || keywordFilter.isEmpty()){
			flag = true;
		}else if(this.getContent()!=null && this.getContent().indexOf(keywordFilter) != -1){
			flag = true;
		}
		return flag;
	}

	public boolean isMatchWrongRate(String wrongRateFilter){
		boolean flag = false;
	
		if(wrongRateFilter==null || wrongRateFilter.isEmpty()){
			flag = true;
		}else if(this.getWrongRate()!=null && this.getWrongRate().equals(wrongRateFilter)){
			flag = true;
		}
		return flag;
	}

	public String getWrongRate(){
		int times = this.rightTimes + this.getWrongTimes();
		Float rate = new Float((float)this.getWrongTimes()/(float)times);
		
		NumberFormat numberFormat = NumberFormat.getInstance();  // 创建一个数值格式化对象  
		numberFormat.setMaximumFractionDigits(0); // 设置精确到小数点后0位  
		return numberFormat.format(rate * 100);
		 
	}
	
	public boolean isMatchWrongTag(String wrongTagFilter){
		boolean flag = false;
		if(wrongTagFilter==null || wrongTagFilter.isEmpty()){
			flag = true;
		}else if(this.getWrongTag()!=null && this.getWrongTag().indexOf(wrongTagFilter) != -1){
			flag = true;
		}
		return flag;
	}
	
	public boolean isMatchKnowledgedTag(String knowledgeTagFilter){
		boolean flag = false;
		if(knowledgeTagFilter==null || knowledgeTagFilter.isEmpty()){
			flag = true;
		}else if(this.getKnowledgeTag()!=null && this.getKnowledgeTag().indexOf(knowledgeTagFilter) != -1){
			flag = true;
		}
		return flag;
	}
	
	public boolean isMatchDifficulty(String difficultyFilter){
		boolean flag = false;
		if(difficultyFilter==null || difficultyFilter.isEmpty()  || !isInteger(difficultyFilter)){
			flag = true;
		}else if(this.getDifficulty().equals(Integer.parseInt(difficultyFilter))){
			//System.out.println("match difficulty is true!");
			flag = true;
		}
		return flag;
	}
	
	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public String getKnowledgeTag() {
		return knowledgeTag;
	}

	public void setKnowledgeTag(String knowledgeTag) {
		this.knowledgeTag = knowledgeTag;
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
	public void setRightTimes(Integer rightTimes) {
		this.rightTimes = rightTimes;
	}
	public Integer getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(Integer wrongTimes) {
		this.wrongTimes = wrongTimes;
	}


}
