package com.rhb.joojoo.repository;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository("QuestionRepositoryImp")
public class QuestionRepositoryImp implements QuestionRepository {

	@Value("${rootPath}")
	private String rootPath;
	

	@Override
	public void update(QuestionEntity question) {
		this.writeToFile(rootPath.substring(6) + question.getId()+".json", question);
	}
	
	private void writeToFile(String jsonfile, Object object){
		ObjectMapper mapper = new ObjectMapper();
    	try {
			mapper.writeValue(new File(jsonfile),object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<QuestionEntity> getQuestions() {
		Map<String,QuestionEntity> questions = new HashMap<String,QuestionEntity>();
		
		QuestionEntity question = null;
		String id = null;
		
		String[] originalImages = this.getOriginalImages();
		for(String originalImage : originalImages){
			question = new QuestionEntity();
			question.setOriginalImage(originalImage);
			question.setId(originalImage);
		
			questions.put(originalImage, question);
		}

		String[] contentImages = this.getContentImages();		
		for(String contentImage : contentImages){
			if(questions.containsKey(contentImage)){
				question = questions.get(contentImage);
				question.setContentImage(contentImage);
			}
		}
		
		String[] jsons = this.getJsons();
		
		for(String json : jsons){
			
			if(questions.containsKey(json.substring(0, json.indexOf(".json")))){
				//System.out.println(json);
				ObjectMapper mapper = new ObjectMapper();
				try {
					QuestionEntity q = mapper.readValue(new File(rootPath.substring(6) + json), QuestionEntity.class);
					question = questions.get(json.substring(0, json.indexOf(".json")));
					question.setContent(q.getContent());
					question.setRightTimes(q.getRightTimes());
					question.setWrongTimes(q.getWrongTimes());
					
					//System.out.println("content: " + q.getContent());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		

		
		return new ArrayList<QuestionEntity>(questions.values());
	}

	private String[] getJsons(){
		String[] jsons = null;
		
        File dir = new File(rootPath.substring(6));
        
        if (dir.exists()) {  
        	jsons = dir.list(new FilenameFilter(){ 
                public boolean accept(File dir,String fname){ 
                    return (fname.toLowerCase().endsWith(".json"));  
                 }});
        }else{
            System.out.println("**********  error: '" + rootPath.substring(6) + "' do not exit");          	
        }
        
        return jsons;
	}

	
	private String[] getImages(String path){
		String[] images = null;
		
        File dir = new File(path);
        
        if (dir.exists()) {  
        	images = dir.list(new FilenameFilter(){ 
                public boolean accept(File dir,String fname){ 
                    return (fname.toLowerCase().endsWith(".jpg"));  
                 }});
        }else{
            System.out.println("**********  error: '" + path + "' do not exit");          	
        }
        
        return images;
	}

	
	
	
	private String[] getOriginalImages() {
        return this.getImages(rootPath.substring(6));
     }

	
	private String[] getContentImages() {
		String contentImagePath = rootPath + "content";
        return this.getImages(contentImagePath.substring(6));
	}

	
}
