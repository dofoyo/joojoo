package com.rhb.joojoo.repository;


public class KnowledgeEntity {
	private String id;
	private String tag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	@Override
	public String toString() {
		return "KnowledgeEntity [id=" + id + ", tag=" + tag + "]";
	}
	
	
	
	
}
