package com.rhb.joojoo.repository;

import java.util.List;

public interface QuestionRepository {
	public List<QuestionEntity> getQuestions();
	public void update(QuestionEntity question);
}
