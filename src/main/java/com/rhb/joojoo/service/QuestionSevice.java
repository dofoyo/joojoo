package com.rhb.joojoo.service;

import java.util.List;
import java.util.Map;

import com.rhb.joojoo.api.QuestionDTO;

public interface QuestionSevice {
	public List<QuestionDTO> getQuestions(String orderBy,String knowledgeTagFilter,String difficultyFilter);
	public void refresh();
	public QuestionDTO getQuestion(String id);
	public void updateContent(String id, String content);
	public void updateKnowledgeTag(String id, String knowledgeTag);
	public void right(String id, int i);
	public void wrong(String id, int i);
	public void updateDifficulty(String id, int i);
	public Map<String,Integer> getKnowledgeTagStatics();
	public Map<String,Integer> getWrongRateStatic();
	public Map<String,Integer> getDifficulty(String wrongRateFilter);
	
}
