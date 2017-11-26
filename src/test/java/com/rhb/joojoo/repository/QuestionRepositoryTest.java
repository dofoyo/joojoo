package com.rhb.joojoo.repository;

import java.util.List;

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
	
	@Test
	public void test3(){
		List<QuestionEntity> questions = questionRepository.getQuestions();
		for(QuestionEntity q : questions){
			System.out.println(q.getId());
			System.out.println(q.getOriginalImage());
			System.out.println(q.getContent());
			System.out.println(q.getContentImage());
		}
	}
}
