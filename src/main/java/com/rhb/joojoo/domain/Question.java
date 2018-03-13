package com.rhb.joojoo.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Question {
	private String id;
	private String content;					// 题目
	private String contentImage;  			// 题目图片
	private Set<String> knowledgeTags;		// 知识点标签（追击、相遇...)
	private String knowledgeTag;			//知识点标签
	private List<Practice> practices;		// 练习
	private Integer difficulty = 0;  		//难度,0-最简单，1-较简单，2-简单，3-难，4较难，5-最难
	
	private int rightTimes = 0;				//正确次数
	private String school; 					//学校
	private Map<String,Wrong> wrongs = new HashMap<String,Wrong>();
	private int deleted = 0;
	
	public boolean isDuration(String duration){
		boolean flag = false;
		if(duration.trim().isEmpty() || this.getInteger(duration) == 0 || this.getInteger(duration) >= this.getDuration()){
			flag = true;
		}
		return flag;
	}
	
	private int getInteger(String str){
		int i = 0;
		try{
			i = Integer.parseInt(str);
		}catch(Exception e){
			
		}
		return i;
	}
	
	public boolean isDuration(int duration){
		boolean flag = false;
		if(duration == 0 || duration >= this.getDuration()){
			flag = true;
		}
		return flag;
	}
	
	public long getDuration(){
		long duration = 1000;
		for(Map.Entry<String, Wrong> entry : this.wrongs.entrySet()){
			Wrong w = entry.getValue();
			if(w.getDuration() < duration){
				duration = w.getDuration();
			}
		}
		return duration;

	}
	
	public Map<String, Wrong> getWrongs() {
		return wrongs;
	}

	public String getWrongTag(String image){
		Wrong w = wrongs.get(image);
		return w.getWrongTag();
	}
	
	public void setWrongTag(String image,String tag){
		Wrong w = wrongs.get(image);
		w.setWrongTag(tag);
	}
	
	public String[] getWrongImages(){
		return wrongs.keySet().toArray(new String[0]);
	}
	
	public String getWrongTagString(){
		StringBuffer sb  = new StringBuffer();
		for(Map.Entry<String, Wrong> entry : this.wrongs.entrySet()){
			Wrong w = entry.getValue();
			sb.append(w.getWrongTag());
			sb.append(" ");
		}		
		return sb.toString();
	}

	public void addWrong(String image,String tag){
		Wrong w = new Wrong();
		w.setWrongImage(image);
		w.setWrongTag(tag);
		this.wrongs.put(image, w);
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public boolean isContentImage(String image){
		return image!=null && image.equals(this.contentImage) ? true : false;
	}
	
	public boolean isWrongImage(String image){
		return wrongs.containsKey(image) ? true : false;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}


	public void addWrongImage(String image){
		Wrong w = new Wrong();
		w.setWrongImage(image);
		this.wrongs.put(image, w);
	}
	
	public void removeWrongImage(String image){
		this.wrongs.remove(image);
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
		return this.wrongs.size();
	}

	public void right(int i){
		this.rightTimes = this.rightTimes + i;
	}
	
	

}
