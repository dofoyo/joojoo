package com.rhb.joojoo.api;

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
import com.rhb.joojoo.service.QuestionSevice;

@RestController
public class ProblemController{
	@Autowired
	QuestionSevice questionService;
	
	@Value("${httpPath}")
	private String httpPath;
	
	@Value("${server.port}")
	private String port;

	
	@GetMapping("/difficulty")
	public ResponseContent<List<DifficultyDTO>> getDifficulty(@RequestParam(value="wrongRateFilter", defaultValue="") String wrongRateFilter) {
		//System.out.println(wrongRateFilter);
		Map<String,Integer> tags = questionService.getDifficulty(wrongRateFilter);
		DifficultyDTO dto;
		List<DifficultyDTO> list = new ArrayList<DifficultyDTO>();
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			list.add(new DifficultyDTO(entry.getKey(), entry.getValue()));
			//System.out.println(entry.getKey()+ ": " + entry.getValue());
		}
		
		Collections.sort(list, new Comparator<DifficultyDTO>(){
			public int compare(DifficultyDTO dto1, DifficultyDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		return new ResponseContent<List<DifficultyDTO>>(ResponseEnum.SUCCESS,list);
	}
	
	@GetMapping("/wrongRate")
	public ResponseContent<List<WrongRateStatisticsDTO>> getWrongRate(){
		Map<String,Integer> tags = questionService.getWrongRateStatic();
		WrongRateStatisticsDTO dto;
		List<WrongRateStatisticsDTO> list = new ArrayList<WrongRateStatisticsDTO>();
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			list.add(new WrongRateStatisticsDTO(entry.getKey(), entry.getValue()));
			//System.out.println(entry.getKey()+ ": " + entry.getValue());
		}
		
		Collections.sort(list, new Comparator<WrongRateStatisticsDTO>(){
			public int compare(WrongRateStatisticsDTO dto1, WrongRateStatisticsDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		return new ResponseContent<List<WrongRateStatisticsDTO>>(ResponseEnum.SUCCESS,list);
	}	
	
	@GetMapping("/wrongTags")
	public ResponseContent<List<WrongTagStatisticsDTO>> getWrongTags(@RequestParam(value="threshold", defaultValue="1") String threshold) {
		Map<String,Integer> tags = questionService.getWrongTagStatics();
		List<WrongTagStatisticsDTO> list = new ArrayList<WrongTagStatisticsDTO>();
		int total = 0;
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			total += entry.getValue();
			list.add(new WrongTagStatisticsDTO(entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(list, new Comparator<WrongTagStatisticsDTO>(){
			public int compare(WrongTagStatisticsDTO dto1, WrongTagStatisticsDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		Float f = Float.parseFloat(threshold);
		
		List<WrongTagStatisticsDTO> list2 = new ArrayList<WrongTagStatisticsDTO>();
		int eighty = 0;
		int other = 0;
		for(WrongTagStatisticsDTO dto : list){
			eighty += dto.getValue();
			if((float)eighty/(float)total > f){
				other += dto.getValue();
			}else{
				list2.add(new WrongTagStatisticsDTO(dto.getName(), dto.getValue()));
			}
		}
		if(f.intValue() < 1){
			list2.add(new WrongTagStatisticsDTO("other", other));
		}
		
		return new ResponseContent<List<WrongTagStatisticsDTO>>(ResponseEnum.SUCCESS,list2);
	}	
	
	
	@GetMapping("/knowledgeTags")
	public ResponseContent<List<KnowledgeTagStatisticsDTO>> getKnowledgeTags(@RequestParam(value="threshold", defaultValue="1") String threshold) {
		Map<String,Integer> tags = questionService.getKnowledgeTagStatics();
		List<KnowledgeTagStatisticsDTO> list = new ArrayList<KnowledgeTagStatisticsDTO>();
		int total = 0;
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			total += entry.getValue();
			list.add(new KnowledgeTagStatisticsDTO(entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(list, new Comparator<KnowledgeTagStatisticsDTO>(){
			public int compare(KnowledgeTagStatisticsDTO dto1, KnowledgeTagStatisticsDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		Float f = Float.parseFloat(threshold);
	
		List<KnowledgeTagStatisticsDTO> list2 = new ArrayList<KnowledgeTagStatisticsDTO>();
		int eighty = 0;
		int other = 0;
		for(KnowledgeTagStatisticsDTO dto : list){
			eighty += dto.getValue();
			if((float)eighty/(float)total > f){
				other += dto.getValue();
			}else{
				list2.add(new KnowledgeTagStatisticsDTO(dto.getName(), dto.getValue()));
			}
		}
		if(f.intValue() < 1){
			list2.add(new KnowledgeTagStatisticsDTO("other", other));
		}
		
		return new ResponseContent<List<KnowledgeTagStatisticsDTO>>(ResponseEnum.SUCCESS,list2);
	}	
	
	@GetMapping("/questions")
	public ResponseContent<List<QuestionDTO>> getQuestions(
			@RequestParam(value="orderBy", defaultValue="") String orderBy,
			@RequestParam(value="keywordFilter", defaultValue="") String keywordFilter,
			@RequestParam(value="knowledgeTagFilter", defaultValue="") String knowledgeTagFilter,
			@RequestParam(value="wrongTagFilter", defaultValue="") String wrongTagFilter,
			@RequestParam(value="difficultyFilter", defaultValue="") String difficultyFilter,
			@RequestParam(value="wrongRateFilter", defaultValue="") String wrongRateFilter,
			@RequestParam(value="count", defaultValue="20") Integer count){
		
		List<QuestionDTO> questions = questionService.getQuestions(orderBy,keywordFilter,knowledgeTagFilter,wrongTagFilter,difficultyFilter,wrongRateFilter);
		
		//System.out.println("keywordFilter: " + keywordFilter);
		//System.out.println("count: " + count);
		
		List<QuestionDTO> list = new ArrayList<QuestionDTO>();
		
		String curl = null;
		QuestionDTO question;
		for(int i=0; i<count && i<questions.size(); i++){
			question = questions.get(i);
			question.setContentImageUrl(this.getImageUrl(question.getContentImage()));
			question.setWorngImageUrls(this.getImageUrl(question.getWorngImages()));
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
    public void updateWrongTag(@RequestParam(value="id") String id, @RequestBody String body){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			QuestionDTO dto = mapper.readValue(body, QuestionDTO.class);
			questionService.updateWrongTag(id, dto.getWrongTag());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("updateKnowledgeTag " + id + ", " + body);
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

}
