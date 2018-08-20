package com.rhb.joojoo.repository;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionRepositoryTest {
	@Autowired
	QuestionRepository questionRepository;
	
	//@Test
	public void test3(){
		List<QuestionEntity> questions = questionRepository.getQuestionEntities();
		for(QuestionEntity q : questions){
			System.out.println(q.getId());
			System.out.println(q.getContent());
		}
	}
	
	//@Test
	public void test() {
		Map<String,String> knowledges = questionRepository.getKnowledges();
		
		for(Map.Entry<String, String> entry : knowledges.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		
	}
}
