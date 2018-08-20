package com.rhb.joojoo.repository;

import java.util.List;
import java.util.Map;

public interface QuestionRepository {
	public List<QuestionEntity> getQuestionEntities();
	public void save(QuestionEntity question);
	public String[] getImages();
	public void deleteImage(String imagename);
	public Map<String,String> getKnowledges();
}
