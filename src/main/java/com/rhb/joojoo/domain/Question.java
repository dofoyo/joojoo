package com.rhb.joojoo.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question {
	private String id;
	private String originalImage;  			// 原题图片
	private String content;					// 题目
	private String contentImage;  			// 题目图片
	private Set<String> knowledgeTags;		// 知识点标签（追击、相遇...)
	private String knowledgeTag;	//知识点标签
	private List<Practice> practices;		// 练习
	private Integer difficulty = 0;  //难度,0-最简单，1-较简单，2-简单，3-难，4较难，5-最难
	
	private int rightTimes = 0;					//正确次数
	private String wrongTag; //错误原因标签
	
	private Set<String> wrongImages = new HashSet<String>();
	
	public void addWrongImage(String image){
		this.wrongImages.add(image);
	}
	
	public void addWrongImages(String[] images){
		for(String image : images){
			this.addWrongImage(image);
		}
	}
	
	public Set<String> getWrongImages() {
		return wrongImages;
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

	public Float getWrongRate(){
		int times = this.rightTimes + this.getWrongTimes();
		return new Float((float)this.getWrongTimes()/(float)times);
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
	public Set<String> getKnowledgeTags() {
		return knowledgeTags;
	}
	public void setKnowledgeTags(Set<String> knowledgeTags) {
		this.knowledgeTags = knowledgeTags;
	}
	public List<Practice> getPractices() {
		return practices;
	}
	public void setPractices(List<Practice> practices) {
		this.practices = practices;
	}
	public int getRightTimes() {
		return rightTimes;
	}
	public void setRightTimes(int rightTimes) {
		this.rightTimes = rightTimes;
	}
	
	public int getWrongTimes() {
		return this.wrongImages.size()+1;
	}

	public void right(int i){
		this.rightTimes = this.rightTimes + i;
	}

}
