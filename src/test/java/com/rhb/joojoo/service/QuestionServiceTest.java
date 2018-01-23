package com.rhb.joojoo.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rhb.joojoo.api.QuestionDTO;
import com.rhb.joojoo.api.ImageDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuestionServiceTest {
	@Autowired
	QuestionSevice questionService;
	
	@Test
	public void testGetQuestions(){
		String path = "D:\\mydocs\\ranluwei\\joojoo\\images\\";
		File file;
		List<QuestionDTO> dtos = questionService.getQuestions("","","","","","");
		for(QuestionDTO dto : dtos){
			for(String wrongImage : dto.getWorngImages()){
				file = new File(path + wrongImage);
				if(!file.exists()){
					System.out.println(dto.getContentImage());
					
				}				
			}

			
		}
	}
	
	//@Test
	public void testGetWrongRateStatic(){
		Map<String,Integer> m = questionService.getWrongRateStatic();
		for(Map.Entry<String,Integer> mapEntry : m.entrySet()){
			System.out.println(mapEntry.getKey() + ": " + mapEntry.getValue());
		}
	}
	
	//@Test
	public void testGetTodoImages(){
		List<ImageDTO> dtos = questionService.getImages("");
		for(ImageDTO dto : dtos){
			System.out.println(dto.toString());
		}
	}
	

}
