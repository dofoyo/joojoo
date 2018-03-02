package com.rhb.joojoo.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhb.joojoo.api.question.QuestionDTO;
import com.rhb.joojoo.api.question.WrongDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionServiceTest {
	@Autowired
	QuestionSevice questionService;
	
	//@Test
	public void testGetQuestions(){
		String orderBy = "";
		String keywordFilter = "";
		String knowledgeTagFilter = "";
		String wrongTagFilter = "";
		String difficultyFilter = "";
		String wrongRateFilter = "";
		List<QuestionDTO> questions = questionService.getQuestions(orderBy, keywordFilter, knowledgeTagFilter, wrongTagFilter, difficultyFilter, wrongRateFilter);
		
		for(QuestionDTO q : questions){
			for(WrongDTO w : q.getWrongs()){
				if(w.getTag() == null || w.getTag().trim().isEmpty()){
					System.out.println(q.getContent() + ", wrongImage=" + w.getImage() + ", wrongTag=" + w.getTag());
				}
			}
		}
		
	}


}
