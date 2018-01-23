package com.rhb.joojoo.repository;

public class QuestionEntity {
	private String id;
	private String content;					// 题目
	private int rightTimes = 0;					//正确次数
	private String KnowledgeTag;				//知识点
	private Integer difficulty = 0;
	private String wrongTag;				//错误点
	private String school; 					//学校
	private String wrongImage;			//错题图片
	private String contentImage;		//题目图片
	private int deleted = 0; 
	
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getWrongImage() {
		return wrongImage;
	}
	public void setWrongImage(String wrongImage) {
		this.wrongImage = wrongImage;
	}
	public String getContentImage() {
		return contentImage;
	}
	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRightTimes() {
		return rightTimes;
	}
	public void setRightTimes(int rightTimes) {
		this.rightTimes = rightTimes;
	}
}
