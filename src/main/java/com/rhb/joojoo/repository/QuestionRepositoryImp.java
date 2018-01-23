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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhb.joojoo.util.FileUtil;
import com.rhb.joojoo.util.ImageTool;

@Repository("QuestionRepositoryImp")
public class QuestionRepositoryImp implements QuestionRepository {

	@Value("${rootPath}")
	private String rootPath;
	
	private final static String imagePath = "images/";

	@Override
	public List<QuestionEntity> getQuestionEntities() {
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		
		String[] jsons = this.getJsons();
		for(String json : jsons){
			try {
				QuestionEntity q = mapper.readValue(new File(rootPath.substring(6) + json), QuestionEntity.class);
				questions.add(q);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return questions;
	}

	@Override
	public void save(QuestionEntity question) {
		System.out.println("save " + rootPath.substring(6) + question.getId()+".json");
		this.writeToFile(rootPath.substring(6) + question.getId()+".json", question);
	}
	
	@Override
	public String[] getImages() {
		String imagePath = rootPath + this.imagePath;
		String[] files = this.getImages(imagePath.substring(6));
		String pathAndFile;
		for(String file : files){
			pathAndFile = imagePath.substring(6) + file;
			ImageTool.scale2(pathAndFile, pathAndFile, 768, 1024, false);
		}
        return files;
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
                    return (fname.toLowerCase().endsWith(".jpg") || fname.toLowerCase().endsWith(".png"));  
                 }});
        }else{
            System.out.println("**********  error: '" + path + "' do not exit");          	
        }
        
        return images;
	}
	
}
