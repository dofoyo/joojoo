package com.rhb.joojoo.repository;

import java.util.List;

public interface QuestionRepository {
	public List<QuestionEntity> getQuestionEntities();
	public String[] getContentImages();
	public String[] getWrongImages();
	public void update(QuestionEntity question);
	public String[] getTodoImages();
}
