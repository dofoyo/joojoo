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
	private String knowledgeTag;	//知识点标签
	private Float wrongRate;
	private Integer difficulty = 0;
	
	private boolean isInteger(String str){
		try {  
	         Integer.parseInt(str);  
	         return true;  
	     } catch (NumberFormatException e) {  
	         return false;  
	     } 
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

	public Float getWrongRate() {
		return wrongRate;
	}

	public void setWrongRate(Float wrongRate) {
		this.wrongRate = wrongRate;
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
