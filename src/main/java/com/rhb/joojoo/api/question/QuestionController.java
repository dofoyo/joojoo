package com.rhb.joojoo.api.question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhb.joojoo.api.ResponseContent;
import com.rhb.joojoo.api.ResponseEnum;
import com.rhb.joojoo.service.QuestionSevice;

@RestController
public class QuestionController{
	@Autowired
	QuestionSevice questionService;
	
	@Value("${httpPath}")
	private String httpPath;
	
	@Value("${server.port}")
	private String port;


	@GetMapping("/questions")
	public ResponseContent<List<QuestionDTO>> getQuestions(
			@RequestParam(value="orderBy", defaultValue="") String orderBy,
			@RequestParam(value="keywordFilter", defaultValue="") String keywordFilter,
			@RequestParam(value="knowledgeTagFilter", defaultValue="") String knowledgeTagFilter,
			@RequestParam(value="wrongTagFilter", defaultValue="") String wrongTagFilter,
			@RequestParam(value="difficultyFilter", defaultValue="") String difficultyFilter,
			@RequestParam(value="wrongRateFilter", defaultValue="") String wrongRateFilter,
			@RequestParam(value="duration", defaultValue="1000") String duration,
			@RequestParam(value="count", defaultValue="20") Integer count){
		
		List<QuestionDTO> questions = questionService.getQuestions(orderBy,keywordFilter,knowledgeTagFilter,wrongTagFilter,difficultyFilter,wrongRateFilter,duration);
		
		//System.out.println("keywordFilter: " + keywordFilter);
		//System.out.println("count: " + count);
		
		List<QuestionDTO> list = new ArrayList<QuestionDTO>();
		
		String curl = null;
		QuestionDTO question;
		for(int i=0; i<count && i<questions.size(); i++){
			question = questions.get(i);
			question.setContentImageUrl(this.getImageUrl(question.getContentImage()));
			//question.setWorngImageUrls(this.getImageUrl(question.getWorngImages()));
			question.setWrongImageUrl(this.getImageUrlPrefix());
			list.add(question);
		}
		
		return new ResponseContent<List<QuestionDTO>>(ResponseEnum.SUCCESS,list);
	}
	
	@PutMapping("/questions")
	public void refresh() {
		//System.out.println("refresh....");
		questionService.refresh();
	}

	@GetMapping("/question")
	public ResponseContent<QuestionDTO> getQuestion(@RequestParam(value="id") String id) {
		//System.out.println("id: " +  id);
		
		QuestionDTO question = questionService.getQuestion(id);
		question.setContentImageUrl(this.getImageUrl(question.getContentImage()));

		return new ResponseContent<QuestionDTO>(ResponseEnum.SUCCESS,question);
	}
	

	
    @PutMapping("/question_content")
    public void updateContent(@RequestParam(value="id") String id, @RequestBody String body){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			QuestionDTO dto = mapper.readValue(body, QuestionDTO.class);
			questionService.updateContent(id, dto.getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("update content " + id + ", " + body);
    }
    
    @PutMapping("/question_knowledgeTag")
    public void updateKnowledgeTag(@RequestParam(value="id") String id, @RequestBody String body){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			QuestionDTO dto = mapper.readValue(body, QuestionDTO.class);
			questionService.updateKnowledgeTag(id, dto.getKnowledgeTag());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("updateKnowledgeTag " + id + ", " + body);
    }

    @PutMapping("/question_wrongTag")
    public void updateWrongTag(@RequestBody Map<String,String> params){
		//System.out.println("updateWrongTag, id is " + params.get("id"));
		//System.out.println("updateWrongTag, wrongImage is " + params.get("wrongImage"));
		//System.out.println("updateWrongTag, wrongTag is " + params.get("wrongTag"));
		questionService.updateWrongTag(params.get("id"),params.get("wrongImage"),params.get("wrongTag"));
	}

    @PutMapping("/question_right")
    public void updateRightTimes(@RequestParam(value="id") String id, @RequestBody String body){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			QuestionDTO dto = mapper.readValue(body, QuestionDTO.class);
			//System.out.println(dto.getRightTimes());
			questionService.right(id, dto.getRightTimes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("update content " + id + ", " + body);
    }
  
 
    @PutMapping("/question_difficulty")
    public void updateDifficulty(@RequestParam(value="id") String id, @RequestBody String body){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			QuestionDTO dto = mapper.readValue(body, QuestionDTO.class);
			//System.out.println(dto.getRightTimes());
			questionService.updateDifficulty(id, dto.getDifficulty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("update content " + id + ", " + body);
    }
    
    private String getImageUrl(String image){
    	String url;
    	if(image!=null && !image.equals("null") && !image.isEmpty()){
    		url = this.httpPath + ":" + this.port + "/images/" + image;
    	}else{
    		url = "";
    	}
    	return url;
    }
    
    private String[] getImageUrl(String[] images){
    	String[] urls = new String[images.length];
    	for(int i=0; i<images.length; i++){
        	if(images[i]!=null && !images[i].equals("null") && !images[i].isEmpty()){
        		urls[i] = this.httpPath + ":" + this.port + "/images/" + images[i];
        	}else{
        		urls[i] = "";
        	}
    	}
    	return urls;
    }
    
    private String getImageUrlPrefix(){
    	return this.httpPath + ":" + this.port + "/images/";
    }

}
