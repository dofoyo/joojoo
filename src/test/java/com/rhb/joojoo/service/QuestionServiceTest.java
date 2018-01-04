package com.rhb.joojoo.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhb.joojoo.api.QuestionDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionServiceTest {
	@Autowired
	QuestionSevice questionService;
	
	//@Test
	public void testGetQuestions(){
		List<QuestionDTO> dtos = questionService.getQuestions("", "","");
		for(QuestionDTO dto : dtos){
			System.out.println(dto.getOriginalImage());
			System.out.println(dto.getContent());
			System.out.println(dto.getContentImage());
			System.out.println(dto.getWrongTimes() + "/(" + dto.getRightTimes() + "+"  + dto.getWrongTimes() + ") = " + dto.getWrongRate());
		}
	}
	
	@Test
	public void testGetWrongRateStatic(){
		Map<String,Integer> m = questionService.getWrongRateStatic();
		for(Map.Entry<String,Integer> mapEntry : m.entrySet()){
			System.out.println(mapEntry.getKey() + ": " + mapEntry.getValue());
		}
	}

}
