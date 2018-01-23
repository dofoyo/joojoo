package com.rhb.joojoo.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhb.joojoo.service.QuestionSevice;

@RestController
public class ImageController{
	@Autowired
	QuestionSevice questionService;
	
	@Value("${httpPath}")
	private String httpPath;
	
	@Value("${server.port}")
	private String port;

	
	//private static final String contentImageUrl = "http://localhost:8081/contentImage/";
	//private static final String wrongImageUrl = "http://localhost:8081/wrongImage/";
	//private static final String todoImageUrl = "http://localhost:8081/todoImage/";

	@PutMapping("/newQuestion")
    public void newQuestion(@RequestBody Map<String,String> params){
		//System.out.println("newQuestion, image is " + params.get("imagename"));
		questionService.createQuestion(params.get("imagename"));
	}

	
	@PutMapping("/reQuestion")
    public void reQuestion(@RequestBody Map<String,String> params){
		//System.out.println("reQuestion, imagename is " + params.get("imagename"));
		//System.out.println("reQuestion, questionid is " + params.get("questionid"));
		//System.out.println("reQuestion, type is " + params.get("type"));
		questionService.setImageToQuestion(params.get("questionid"), params.get("imagename"), Integer.parseInt(params.get("type")));
	}


	@PutMapping("/cancel")
    public void cancel(@RequestBody Map<String,String> params){
		//System.out.println("cancel, image is " + params.get("imagename"));
		questionService.cancel(params.get("imagename"));
	}
	
	@GetMapping("/images")
	public ResponseContent<List<ImageDTO>> getImages(
			@RequestParam(value="count", defaultValue="20") String count,
			@RequestParam(value="imagenameFilter",defaultValue="") String imagenameFilter){
		//System.out.println("********* get todo images  ********");
		List<ImageDTO> todos = questionService.getImages(imagenameFilter);
		//System.out.println("*********** There are " + images.length + " images**************");
		
		List<ImageDTO> list = new ArrayList<ImageDTO>();
		ImageDTO dto;
		for(int i=0; i<Integer.parseInt(count) && i<todos.size(); i++){
			dto = todos.get(i);
			dto.setUrl(this.getImageUrl(dto.getName()));
			list.add(dto);
		}

		return new ResponseContent<List<ImageDTO>>(ResponseEnum.SUCCESS,list);
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


}
