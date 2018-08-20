package com.rhb.joojoo.api.question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsDTO {
	private String knowledgeID;
	private String knowledgeName;
	
	private List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
	

	public String getKnowledgeID() {
		return knowledgeID;
	}
	public void setKnowledgeID(String knowledgeID) {
		this.knowledgeID = knowledgeID;
	}
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	public List<QuestionDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(QuestionDTO dto) {
		this.questions.add(dto);
	}
	@Override
	public String toString() {
		return "QuestionsDTO [knowledgeID=" + knowledgeID + ", knowledgeName=" + knowledgeName + ", questions="
				+ questions + "]";
	}
	
	
}
