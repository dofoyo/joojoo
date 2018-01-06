package com.rhb.joojoo.repository;

public class QuestionEntity {
	private String id;
	private String originalImage;  			// 原题图片
	private String content;					// 题目
	private String contentImage;  			// 题目图片
	private int rightTimes = 0;					//正确次数
	private int wrongTimes = 1;					//错误次数
	private String KnowledgeTag;				//知识点
	private Integer difficulty = 0;
	private String wrongTag;				//错误点
	
	
	
	public String getWrongTag() {
		return wrongTag;
	}
	public void setWrongTag(String wrongTag) {
		this.wrongTag = wrongTag;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public String getKnowledgeTag() {
		return KnowledgeTag;
	}
	public void setKnowledgeTag(String knowledgeTag) {
		KnowledgeTag = knowledgeTag;
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
	public int getRightTimes() {
		return rightTimes;
	}
	public void setRightTimes(int rightTimes) {
		this.rightTimes = rightTimes;
	}
	public int getWrongTimes() {
		return wrongTimes;
	}
	public void setWrongTimes(int wrongTimes) {
		this.wrongTimes = wrongTimes;
	}



}
