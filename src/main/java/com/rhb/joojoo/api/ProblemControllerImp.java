package com.rhb.joojoo.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhb.joojoo.service.QuestionSevice;

@RestController
public class ProblemControllerImp implements ProblemController {
	@Autowired
	QuestionSevice questionService;
	
	private static final String contentImageUrl = "http://localhost:8081/contentImage/";
	private static final String originalImageUrl = "http://localhost:8081/originalImage/";
	
	
	@Override
	@GetMapping("/questions")
	public ResponseContent<List<QuestionDTO>> getQuestions(@RequestParam(value="orderBy", defaultValue="") String orderBy) {
		List<QuestionDTO> questions = null;
		//System.out.println("orderBy:" + orderBy);
		questions = questionService.getQuestions(orderBy);
		
		String curl = null;
		for(QuestionDTO question : questions){
			question.setContentImageUrl(this.getContentImageUrl(question.getContentImage()));
			question.setOriginalImageUrl(originalImageUrl + question.getOriginalImage());
		}
		
		return new ResponseContent<List<QuestionDTO>>(ResponseEnum.SUCCESS,questions);
	}
	
	@Override
	@PutMapping("/questions")
	public void refresh() {
		//System.out.println("refresh....");
		questionService.refresh();
	}

	@Override
	@GetMapping("/question")
	public ResponseContent<QuestionDTO> getQuestion(@RequestParam(value="id") String id) {
		//System.out.println("id: " +  id);
		
		QuestionDTO question = questionService.getQuestion(id);
		String orul = originalImageUrl + question.getOriginalImage();
		question.setContentImageUrl(this.getContentImageUrl(question.getContentImage()));
		question.setOriginalImageUrl(orul);

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
  
    @PutMapping("/question_wrong")
    public void updateWrongTimes(@RequestParam(value="id") String id, @RequestBody String body){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			QuestionDTO dto = mapper.readValue(body, QuestionDTO.class);
			questionService.wrong(id, dto.getWrongTimes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//System.out.println("update content " + id + ", " + body);
    }
    
    
    private String getContentImageUrl(String contentImage){
    	String url;
    	if(contentImage!=null && !contentImage.equals("null") && !contentImage.isEmpty()){
    		url = contentImageUrl + contentImage;
    	}else{
    		url = "";
    	}
    	return url;
    }

}
