package com.rhb.joojoo.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

public interface ProblemController {
	public ResponseContent<List<QuestionDTO>> getQuestions(String orderBy);
	public void refresh();
	public ResponseContent<QuestionDTO> getQuestion(@RequestParam(value="id", defaultValue="1") String id);
	

}
