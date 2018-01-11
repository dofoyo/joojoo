package com.rhb.joojoo.api;

import java.util.List;

public interface ProblemController {
	public ResponseContent<List<QuestionDTO>> getQuestions(String orderBy, String knowledgeTagFilter,String worngTagFilter,String difficultyFilter);
	public void refresh();
	public ResponseContent<QuestionDTO> getQuestion(String id);
	

}
