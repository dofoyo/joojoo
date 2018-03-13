package com.rhb.joojoo.api.question;

import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class QuestionDTO {
	
	private String id;
	private String content;					// 题目
	private String contentImage;  			// 题目图片
	private String contentImageUrl;
	private Integer rightTimes = 0;					//正确次数
	private String knowledgeTag;	//知识点标签
	private Integer difficulty = 0;
	private String duration;
	private Set<WrongDTO> wrongs = new HashSet<WrongDTO>();
	
	//private String wrongTag;	//错误点
	//private String[] worngImages; //错题图片
	//private String[] worngImageUrls;  

	private String school; 					//学校
	


	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Integer getMadeDate(){
		Integer madeDate = 0;
		for(WrongDTO wrong : wrongs){
			if(wrong.getMadeDate().compareTo(madeDate) == 1){
				madeDate = wrong.getMadeDate();
			}
		}
		return madeDate;
	}
	
	public Set<WrongDTO> getWrongs() {
		return wrongs;
	}

	public void setWrongs(Set<WrongDTO> wrongs) {
		this.wrongs = wrongs;
	}

	public void addWrong(String image, String tag){
		WrongDTO w = new WrongDTO();
		w.setImage(image);
		w.setTag(tag);
		this.wrongs.add(w);
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public void setWrongImageUrl(String urlPrefix){
		for(WrongDTO w : this.wrongs){
			w.setImageUrl(urlPrefix + w.getImage()); 
		}
	}

/*	public String[] getWorngImageUrls() {
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
	public void setWrongTag(String wrongTag) {
		this.wrongTag = wrongTag;
	}	
	*/

	public String getWrongTag() {
		StringBuffer sb  = new StringBuffer();
		for(WrongDTO w : this.wrongs){
			sb.append(w.getTag());
			sb.append(" ");
		}		
		return sb.toString();
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
		//System.out.println("keyworkFilter is " + keywordFilter);
		boolean flag = false;
		if(keywordFilter==null || keywordFilter.isEmpty()){
			//System.out.println("keyworkFilter is empty!");
			flag = true;
		}else if(this.getContent()!=null && this.getContent().contains(keywordFilter)){
			flag = true;
			//System.out.println("keyworkFilter is match the content:'" + this.getContent() + "'");
		}
		return flag;
	}

	public Integer getWrongRate(){
		int times = this.rightTimes + this.getWrongTimes();
		Float rate = new Float((float)this.getWrongTimes()/(float)times) * 100;
		return rate.intValue();
/*		NumberFormat numberFormat = NumberFormat.getInstance();  // 创建一个数值格式化对象  
		numberFormat.setMaximumFractionDigits(0); // 设置精确到小数点后0位  
		return numberFormat.format(rate);*/
		 
	}
	
	public boolean isMatchWrongTag(String wrongTagFilter){
		boolean flag = false;
		if(wrongTagFilter==null || wrongTagFilter.isEmpty()){
			flag = true;
		}else{
			for(WrongDTO w : wrongs){
				if(w.getTag()!=null && w.getTag().indexOf(wrongTagFilter) != -1){
					flag = true;
					break;
				}
			}
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

		if(difficultyFilter==null || difficultyFilter.isEmpty()){
			flag = true;
		}else{
			String operator = "";
			if(difficultyFilter.indexOf("<") == -1 && difficultyFilter.indexOf("=") == -1 && difficultyFilter.indexOf(">") == -1){
				operator = "==";
			}
			
			String str = Integer.toString(difficulty) + operator + difficultyFilter;
			//System.out.println(str);
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			try {
				flag = (Boolean)engine.eval(str);
				//System.out.println(flag);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}			
		}
		
		return flag;
	}
	
	public boolean isMatchWrongRate(String wrongRateFilter){
		boolean flag = false;

		if(wrongRateFilter==null || wrongRateFilter.isEmpty()){
			flag = true;
		}else{
			String operator = "";
			if(wrongRateFilter.indexOf("<") == -1 && wrongRateFilter.indexOf("=") == -1 && wrongRateFilter.indexOf(">") == -1){
				operator = "==";
			}
			
			String str = Integer.toString(this.getWrongRate()) + operator + wrongRateFilter;
			//System.out.println(str);
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			try {
				flag = (Boolean)engine.eval(str);
				//System.out.println(flag);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}			
		}
		
		return flag;
	}
	
/*	public boolean isMatchWrongRate(String wrongRateFilter){
		boolean flag = false;
	
		if(wrongRateFilter==null || wrongRateFilter.isEmpty()){
			flag = true;
		}else if(this.getWrongRate()!=null && this.getWrongRate().equals(Integer.parseInt(wrongRateFilter))){
			flag = true;
		}
		return flag;
	}*/
	
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
		return this.wrongs.size();
	}




}
