package com.rhb.joojoo.api;

public class ImageDTO {
	private String name;
	private String url;
	private Integer type = -1; //-1表示未指定，1表示指定为错题图片，0表示指定为题图片
	private String content;
	private String questionid;
	
	public ImageDTO(){
		super();
	}
	
	public ImageDTO(String name){
		super();
		this.name = name;
	}
	
	public boolean isMatchName(String nameFilter){
		boolean flag = false;
		if(nameFilter==null || nameFilter.isEmpty()){
			flag = true;
		}else if(this.getName()!=null && this.getName().indexOf(nameFilter) != -1){
			flag = true;
		}
		return flag;
	}
	
	public boolean isMatchType(String typeFilter){
		boolean flag = false;
		if(typeFilter==null || typeFilter.isEmpty()){
			flag = true;
		}else if(this.type == Integer.parseInt(typeFilter)){
			//System.out.println(this.type);
			//System.out.println(typeFilter);
			flag = true;
		}
		return flag;
	}
	
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "TodoImageDTO [name=" + name + ", url=" + url + ", type=" + type + ", content=" + content
				+ ", questionid=" + questionid + "]";
	}
	
	
	
	
		
	
}
