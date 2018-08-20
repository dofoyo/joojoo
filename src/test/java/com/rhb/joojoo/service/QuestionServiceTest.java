package com.rhb.joojoo.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhb.joojoo.api.question.QuestionDTO;
import com.rhb.joojoo.api.question.QuestionsDTO;
import com.rhb.joojoo.api.question.WrongDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionServiceTest {
	@Autowired
	QuestionSevice questionService;
	
	//@Test
	public void testGetQuestions(){
		String orderBy = "orderByKnowledge";
		String keywordFilter = "";
		String knowledgeTagFilter = "";
		String wrongTagFilter = "";
		String difficultyFilter = "";
		String wrongRateFilter = "";
		String duration = "1000";
		List<QuestionDTO> questions = questionService.getQuestions(orderBy, keywordFilter, knowledgeTagFilter, wrongTagFilter, difficultyFilter, wrongRateFilter,duration);
		
		for(QuestionDTO q : questions){
			System.out.println(q.toString());
		}
	}
	
	//@Test
	public void test(){
		Map<String,Integer> m = questionService.getKnowledgeTagStatics(7);
		for(Map.Entry<String, Integer> entry : m.entrySet()){
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}

	}
	
	@Test
	public void test1() {
		List<QuestionsDTO> questions = questionService.getQuestionsByKnowledge();
		
		for(QuestionsDTO q : questions){
			System.out.println(q.toString());
		}
	}


}
