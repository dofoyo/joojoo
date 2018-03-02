package com.rhb.joojoo.api.question;

public class QuestionQuery {
	private String orderBy = "";
	private String keywordFilter = "";
	private String knowledgeTagFilter = "";
	private String wrongTagFilter = "";
	private String difficultyFilter = "";
	private String wrongRateFilter = "";
	private Integer count = 20;
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getKeywordFilter() {
		return keywordFilter;
	}
	public void setKeywordFilter(String keywordFilter) {
		this.keywordFilter = keywordFilter;
	}
	public String getKnowledgeTagFilter() {
		return knowledgeTagFilter;
	}
	public void setKnowledgeTagFilter(String knowledgeTagFilter) {
		this.knowledgeTagFilter = knowledgeTagFilter;
	}
	public String getWrongTagFilter() {
		return wrongTagFilter;
	}
	public void setWrongTagFilter(String wrongTagFilter) {
		this.wrongTagFilter = wrongTagFilter;
	}
	public String getDifficultyFilter() {
		return difficultyFilter;
	}
	public void setDifficultyFilter(String difficultyFilter) {
		this.difficultyFilter = difficultyFilter;
	}
	public String getWrongRateFilter() {
		return wrongRateFilter;
	}
	public void setWrongRateFilter(String wrongRateFilter) {
		this.wrongRateFilter = wrongRateFilter;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "QuestionQuery [orderBy=" + orderBy + ", keywordFilter=" + keywordFilter + ", knowledgeTagFilter="
				+ knowledgeTagFilter + ", wrongTagFilter=" + wrongTagFilter + ", difficultyFilter=" + difficultyFilter
				+ ", wrongRateFilter=" + wrongRateFilter + ", count=" + count + "]";
	}

	
}
