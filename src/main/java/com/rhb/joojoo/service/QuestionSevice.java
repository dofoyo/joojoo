package com.rhb.joojoo.service;

import java.util.List;
import java.util.Map;

import com.rhb.joojoo.api.QuestionDTO;
import com.rhb.joojoo.api.ImageDTO;

public interface QuestionSevice {
	public List<QuestionDTO> getQuestions(
			String orderBy,
			String keywordFilter,
			String knowledgeTagFilter,
			String wrongTagFilter,
			String difficultyFilter,
			String wrongRateFilter);
	public void refresh();
	public QuestionDTO getQuestion(String id);
	public void updateContent(String id, String content);
	public void updateKnowledgeTag(String id, String knowledgeTag);
	public void updateWrongTag(String id, String wrongTag);
	public void right(String id, int i);
	public void updateDifficulty(String id, int i);
	public Map<String,Integer> getKnowledgeTagStatics();
	public Map<String,Integer> getWrongTagStatics();
	public Map<String,Integer> getWrongRateStatic();
	public Map<String,Integer> getDifficulty(String wrongRateFilter);
	public void init();
	public List<ImageDTO> getImages(String imagenameFilter);
	
	public void createQuestion(String image);
	public void setImageToQuestion(String questionid, String imagename, Integer type);
	public void cancel(String image);
	
}
