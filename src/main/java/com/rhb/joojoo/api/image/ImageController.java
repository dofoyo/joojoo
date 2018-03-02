package com.rhb.joojoo.api.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rhb.joojoo.api.ResponseContent;
import com.rhb.joojoo.api.ResponseEnum;
import com.rhb.joojoo.service.QuestionSevice;

@RestController
public class ImageController{
	@Autowired
	QuestionSevice questionService;

	@Value("${rootPath}")
	private String rootPath;
	
	private final static String imagePath = "images/";
	
	@Value("${httpPath}")
	private String httpPath;
	
	@Value("${server.port}")
	private String port;

    @PostMapping("/image")
    public ResponseContent<String> handleFileUpload(@RequestParam("image") MultipartFile file,@RequestParam("questionid") String questionid) {
		
    	String imageNameAndPath = rootPath.substring(6) + this.imagePath + file.getOriginalFilename();
    	
    	File dest = new File(imageNameAndPath);
    	
    	ResponseContent<String> rs = null;
    	try {
			file.transferTo(dest);
			questionService.addImage(file.getOriginalFilename());
			questionService.setImageToQuestion(questionid, file.getOriginalFilename(), 0);
			rs = new ResponseContent<String>(ResponseEnum.SUCCESS,this.getImageUrl(file.getOriginalFilename()));
		} catch (IllegalStateException | IOException e) {
			rs = new ResponseContent<String>(ResponseEnum.ERROR,"");
			e.printStackTrace();
		} 
    	
        return rs;
    }
	

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

	@PutMapping("/delete")
    public void delete(@RequestBody Map<String,String> params){
		//System.out.println("delete, image is " + params.get("imagename"));
		questionService.deleteImage(params.get("imagename"));
	}
	
	@GetMapping("/images")
	public ResponseContent<List<ImageDTO>> getImages(
			@RequestParam(value="count", defaultValue="20") String count,
			@RequestParam(value="typeFilter", defaultValue="") String typeFilter,
			@RequestParam(value="imagenameFilter",defaultValue="") String imagenameFilter){
		//System.out.println("typeFilter: " + typeFilter);
		//System.out.println("********* get todo images  ********");
		List<ImageDTO> todos = questionService.getImages(imagenameFilter,typeFilter);
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
