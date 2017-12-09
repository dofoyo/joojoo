package com.rhb.joojoo.service;

import java.util.List;

import com.rhb.joojoo.api.QuestionDTO;

public interface QuestionSevice {
	public List<QuestionDTO> getQuestions(String orderBy);
	public void refresh();
	public QuestionDTO getQuestion(String id);
	public void updateContent(String id, String content);
	public void updateKnowledgeTag(String id, String knowledgeTag);
	public void right(String id, int i);
	public void wrong(String id, int i);
	
}
