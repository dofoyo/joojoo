package com.rhb.joojoo.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhb.joojoo.api.QuestionDTO;
import com.rhb.joojoo.api.ImageDTO;
import com.rhb.joojoo.domain.Question;
import com.rhb.joojoo.repository.QuestionEntity;
import com.rhb.joojoo.repository.QuestionRepository;
import com.rhb.joojoo.util.FileUtil;

@Service("QuestionServiceImp")
public class QuestionServiceImp implements QuestionSevice{

	@Autowired
	QuestionRepository questionRepository;
	
	private Map<String,Question> questions = null;
	private Map<String,ImageDTO> images = null;
	
	@Override
	public List<QuestionDTO> getQuestions(
			String orderBy,
			String keywordFilter,
			String knowledgeTagFilter,
			String wrongTagFilter, 
			String difficultyFilter,
			String wrongRateFilter) {
		
		List<QuestionDTO> dtos = new ArrayList<QuestionDTO>();
		QuestionDTO dto;
		
		for(Map.Entry<String, Question> entry : questions.entrySet()){
			if(entry.getValue().getDeleted()==0){
				 dto = this.getQuestionDTO(entry.getValue());
				 if(dto.isMatchKnowledgedTag(knowledgeTagFilter) &&
						 dto.isMatchDifficulty(difficultyFilter) && 
						 dto.isMatchKeyword(keywordFilter) && 
						 dto.isMatchWrongTag(wrongTagFilter) &&
						 dto.isMatchWrongRate(wrongRateFilter)){
					 dtos.add(dto);				 
				 }				
			}
		}
		
		if("orderByContent".equals(orderBy)){
			Collections.sort(dtos, new Comparator<QuestionDTO>(){
				public int compare(QuestionDTO dto1, QuestionDTO dto2){			
					String s1 = dto1.getContent()==null ? "" : dto1.getContent();
					String s2 = dto2.getContent()==null ? "" : dto2.getContent();
					
					return s1.compareTo(s2);
				}
			});
		}else{
			Collections.sort(dtos, new Comparator<QuestionDTO>(){
				public int compare(QuestionDTO dto1, QuestionDTO dto2){
					Integer rate2 = dto2.getWrongRate();
					Integer rate1 = dto1.getWrongRate();
					int flag = rate2.compareTo(rate1);
					if(flag == 0){
						flag = dto1.getDifficulty().compareTo(dto2.getDifficulty());
					}
					if(flag == 0){
						flag = dto2.getWrongTimes().compareTo(dto1.getWrongTimes());
					}					
					return flag;
				}
			});
		}
		return dtos;
	}
	
	public void init(){
		System.out.println(" QuestionServiceImp init begin...........");
		this.initQuestions();
		this.initImages();
		System.out.println(" QuestionServiceImp init over...........");
	}
	
	private void initQuestions(){
		questions = new HashMap<String,Question>();
		List<QuestionEntity> QuestionEntities = questionRepository.getQuestionEntities();
		
		Question question = null;
		
		for(QuestionEntity q : QuestionEntities){
			question = new Question();
			question.setId(q.getId());
			question.setContent(q.getContent());
			question.setRightTimes(q.getRightTimes());
			question.setKnowledgeTag(q.getKnowledgeTag());
			question.setDifficulty(q.getDifficulty());
			question.setWrongTag(q.getWrongTag());
			question.setSchool(q.getSchool());
			question.setContentImage(q.getContentImage());
			question.addWrongImages(q.getWrongImage().split(","));
			question.setDeleted(q.getDeleted());
			
			questions.put(question.getId(), question);
		}	
	}
	
	private void initImages(){
		ImageDTO dto;
		this.images = new HashMap<String,ImageDTO>();
		String[] ss =questionRepository.getImages(); 
		for(String image : ss){
			dto = new ImageDTO();
			dto.setName(image);
			for(Map.Entry<String, Question> entry : questions.entrySet()){
				if(entry.getValue().isContentImage(image) || entry.getValue().isWrongImage(image)){
					dto.setQuestionid(entry.getValue().getId());
					dto.setContent(entry.getValue().getContent());
					if(entry.getValue().isContentImage(image)){
						dto.setType(0);
					}else{
						dto.setType(1);
					}
					break;
				}
			}
			images.put(image, dto);
		}
	}
	
	@Override
	public QuestionDTO getQuestion(String id) {	
		Question question = questions.get(id);
		QuestionDTO dto = this.getQuestionDTO(question);
		return dto;
	}

	

	@Override
	public void setImageToQuestion(String questionid, String imagename, Integer type) {
		Question q = this.questions.get(questionid);

		ImageDTO image = this.images.get(imagename);
		image.setType(0);
		image.setQuestionid(q.getId());
		image.setContent(q.getContent());
		
		if(type == 0){
			q.setContentImage(imagename);
		}else if(type == 1){
			q.addWrongImage(imagename);
		}
		
		this.persist(q);
	}
	
	
	@Override
	public void createQuestion(String imagename){
		ImageDTO image = this.images.get(imagename);
		image.setType(1);
		image.setQuestionid(imagename);
		
		Question question = new Question();
		question.setId(imagename);
		question.addWrongImage(imagename);
		questions.put(imagename, question);
		this.persist(question);
	}
	
	@Override
	public void cancel(String imagename){
		ImageDTO image = this.images.get(imagename);
		image.setType(-1);
		image.setQuestionid("");
		image.setContent("");
		
		for(Map.Entry<String, Question> entry : questions.entrySet()){
			if(entry.getValue().isContentImage(imagename) ||
					entry.getValue().isWrongImage(imagename)){
				if(entry.getValue().isContentImage(imagename)){
					entry.getValue().setContentImage("");
				}else if(entry.getValue().isWrongImage(imagename)){
					entry.getValue().removeWrongImage(imagename);
				}
				this.persist(entry.getValue());
				break;
			}
		}
	}

	@Override
	public void updateContent(String id, String content) {
		//System.out.println("questionServiceImp.updateContent(" + id + "," + content + ")");	
		if(questions.containsKey(id)){
			Question question = questions.get(id);
			//更新domain对象
			question.setContent(content);
			//持久化
			this.persist(question);
		}
	}

	@Override
	public void updateKnowledgeTag(String id, String knowledgeTag) {
		//System.out.println("questionServiceImp.updateContent(" + id + "," + content + ")");	
		if(questions.containsKey(id)){
			Question question = questions.get(id);
			//更新domain对象
			question.setKnowledgeTag(knowledgeTag);
			//持久化
			this.persist(question);
		}
	}

	@Override
	public void updateWrongTag(String id, String wrongTag) {
		//System.out.println("questionServiceImp.updateContent(" + id + "," + content + ")");	
		if(questions.containsKey(id)){
			Question question = questions.get(id);
			//更新domain对象
			question.setWrongTag(wrongTag);
			//持久化
			this.persist(question);
		}
	}
	
	@Override
	public void refresh() {
		System.out.println("refresh......");
		this.init();
		
	}

	@Override
	public void right(String id, int i) {
		if(questions.containsKey(id)){
			Question question = questions.get(id);
			
			question.setRightTimes(i);;
			//持久化
			this.persist(question);
		}		
	}


	private void persist(Question question){
		QuestionEntity qe = new QuestionEntity();
		qe.setId(question.getId());
		qe.setContent(question.getContent());
		qe.setRightTimes(question.getRightTimes());
		qe.setKnowledgeTag(question.getKnowledgeTag());
		qe.setDifficulty(question.getDifficulty());
		qe.setWrongTag(question.getWrongTag());
		qe.setSchool(question.getSchool());
		qe.setContentImage(question.getContentImage());
		qe.setWrongImage(question.getWrongImagesString());
		qe.setDeleted(question.getDeleted());
		questionRepository.save(qe);
	}
	
	private QuestionDTO getQuestionDTO(Question question){
		QuestionDTO dto = new QuestionDTO();
		dto.setId(question.getId());
		dto.setContent(question.getContent());
		dto.setContentImage(question.getContentImage());
		dto.setRightTimes(question.getRightTimes());
		dto.setWrongTimes(question.getWrongTimes());
		dto.setKnowledgeTag(question.getKnowledgeTag());
		dto.setDifficulty(question.getDifficulty());
		dto.setWrongTag(question.getWrongTag());
		dto.setSchool(question.getSchool());
		dto.setWorngImages(question.getWrongImages().toArray(new String[0]));
		return dto;
	}
	
	@Override
	public Map<String,Integer> getWrongRateStatic(){
		NumberFormat numberFormat = NumberFormat.getInstance();  // 创建一个数值格式化对象  
		numberFormat.setMaximumFractionDigits(0); // 设置精确到小数点后2位  
		
		Map<String, Integer> statics = new HashMap<String,Integer>();
		
		String wrongRate;
		Integer i;		
		for(Map.Entry<String, Question> entry : questions.entrySet()){

			wrongRate = numberFormat.format(entry.getValue().getWrongRate()* 100);
			//System.out.println(entry.getKey() + ": " + entry.getValue() + ", wrongRate: " + wrongRate);
			//wrongRate = new java.text.DecimalFormat(".##").format(entry.getValue().getWrongRate());
			if(statics.containsKey(wrongRate)){
				i = statics.get(wrongRate) + 1;
			}else{
				i = 1;
			}
			statics.put(wrongRate, i);
		}		
		return statics;

	}

	@Override
	public Map<String, Integer> getWrongTagStatics() {	
		Map<String, Integer> statics = new HashMap<String,Integer>();
		String[] tags;
		for(Map.Entry<String, Question> entry : questions.entrySet()){
			if(entry.getValue().getWrongTag()!=null){
				tags = entry.getValue().getWrongTag().split(" ");
				for(String tag : tags){
					if(!tag.trim().isEmpty()){
						if(statics.containsKey(tag)){
							statics.put(tag, statics.get(tag) + 1);
						}else{
							statics.put(tag, 1);
						}	
					}
				}
			}
		}
		
		return statics;
	}
	
	@Override
	public Map<String, Integer> getKnowledgeTagStatics() {
		Map<String, Integer> statics = new HashMap<String,Integer>();
		String[] tags;
		for(Map.Entry<String, Question> entry : questions.entrySet()){
			if(entry.getValue().getKnowledgeTag()!=null){
				tags = entry.getValue().getKnowledgeTag().split(" ");
				for(String tag : tags){
					if(!tag.trim().isEmpty()){
						if(statics.containsKey(tag)){
							statics.put(tag, statics.get(tag) + 1);
						}else{
							statics.put(tag, 1);
						}	
					}
				}
			}
		}
		
		return statics;
	}

	@Override
	public Map<String, Integer> getDifficulty(String wrongRateFilter) {
		NumberFormat numberFormat = NumberFormat.getInstance();  // 创建一个数值格式化对象  
		numberFormat.setMaximumFractionDigits(2); // 设置精确到小数点后2位  

		
		Map<String, Integer> statics = new HashMap<String,Integer>();
		String[] tags;
		String diff;
		String wrongRate;
		for(Map.Entry<String, Question> entry : questions.entrySet()){
			wrongRate = numberFormat.format(entry.getValue().getWrongRate()* 100);
			
			if(wrongRateFilter.trim().isEmpty()){
				diff = entry.getValue().getDifficulty().toString();
				if(statics.containsKey(diff)){
					statics.put(diff, statics.get(diff) + 1);
				}else{
					statics.put(diff, 1);
				}	
			}else{
				if(wrongRate.equals(wrongRateFilter)){
					//System.out.println("there is one match " + wrongRateFilter);
					diff = entry.getValue().getDifficulty().toString();
					if(statics.containsKey(diff)){
						statics.put(diff, statics.get(diff) + 1);
					}else{
						statics.put(diff, 1);
					}
				}
			}
		}
		
		return statics;
	}
	

	@Override
	public void updateDifficulty(String id, int i) {
		if(questions.containsKey(id)){
			Question question = questions.get(id);
			
			question.setDifficulty(i);;
			//持久化
			this.persist(question);
		}				
	}

	@Override
	public List<ImageDTO> getImages(String imagenameFilter) {
		List<ImageDTO> list = new ArrayList<ImageDTO>();
		for(Map.Entry<String, ImageDTO> entry : images.entrySet()){
			if(entry.getValue().isMatchName(imagenameFilter)){
				list.add(entry.getValue());
			}
		}
		
		Collections.sort(list, new Comparator<ImageDTO>(){
			public int compare(ImageDTO dto1, ImageDTO dto2){			
				return dto1.getType().compareTo(dto2.getType());
			}
		});
		return list;
	}

	


}
