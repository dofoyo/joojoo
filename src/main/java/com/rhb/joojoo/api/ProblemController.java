package com.rhb.joojoo.api;

import java.util.List;

public interface ProblemController {
	public ResponseContent<List<QuestionDTO>> getQuestions(
			String orderBy,
			String keywordFilter,
			String knowledgeTagFilter,
			String worngTagFilter,
			String difficultyFilter,
			String wrongRateFilter,
			Integer count);
	public void refresh();
	public ResponseContent<QuestionDTO> getQuestion(String id);
	

}
