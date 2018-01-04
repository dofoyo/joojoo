package com.rhb.joojoo.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhb.joojoo.api.QuestionDTO;
import com.rhb.joojoo.domain.Question;
import com.rhb.joojoo.repository.QuestionEntity;
import com.rhb.joojoo.repository.QuestionRepository;

@Service("QuestionServiceImp")
public class QuestionServiceImp implements QuestionSevice{

	@Autowired
	QuestionRepository questionRepository;
	
	private Map<String,Question> questions = null;

	@Override
	public List<QuestionDTO> getQuestions(String orderBy,String knowledgeTagFilter, String difficultyFilter) {
		//System.out.println("orderBy:" + orderBy);
		//System.out.println("filterStr:" + filterStr);
		if(questions == null){
			init();
		}
		
		List<QuestionDTO> dtos = new ArrayList<QuestionDTO>();
		QuestionDTO dto;
		
		for(Map.Entry<String, Question> entry : questions.entrySet()){
			 dto = this.getQuestionDTO(entry.getValue());
			 if(dto.isMatchKnowledgedTag(knowledgeTagFilter) && dto.isMatchDifficulty(difficultyFilter)){
				 dtos.add(dto);				 
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
					int flag = dto2.getWrongRate().compareTo(dto1.getWrongRate());
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
	
	private void init(){
		System.out.println(" QuestionServiceImp init ...........");
		
		questions = new HashMap<String,Question>();
		Question question = null;
		
		List<QuestionEntity> QuestionEntities = questionRepository.getQuestions();
		for(QuestionEntity q : QuestionEntities){
			question = new Question();
			question.setOriginalImage(q.getOriginalImage());
			question.setId(q.getId());
			question.setContentImage(q.getContentImage());
			question.setContent(q.getContent());
			question.setRightTimes(q.getRightTimes());
			question.setWrongTimes(q.getWrongTimes());
			question.setKnowledgeTag(q.getKnowledgeTag());
			question.setDifficulty(q.getDifficulty());
			questions.put(question.getId(), question);
		}
		
	}

	@Override
	public QuestionDTO getQuestion(String id) {
		if(questions == null){
			init();
		}
		
		Question question = questions.get(id);
		QuestionDTO dto = this.getQuestionDTO(question);
		return dto;
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
	public void refresh() {
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

	@Override
	public void wrong(String id, int i) {
		if(questions.containsKey(id)){
			Question question = questions.get(id);
			
			question.setWrongTimes(i);
			//持久化
			this.persist(question);
		}		
	}
	
	private void persist(Question question){
		QuestionEntity qe = new QuestionEntity();
		qe.setId(question.getId());
		qe.setContent(question.getContent());
		qe.setContentImage(question.getContentImage());
		qe.setOriginalImage(question.getOriginalImage());
		qe.setRightTimes(question.getRightTimes());
		qe.setWrongTimes(question.getWrongTimes());
		qe.setKnowledgeTag(question.getKnowledgeTag());
		qe.setDifficulty(question.getDifficulty());
		questionRepository.update(qe);
	}
	
	private QuestionDTO getQuestionDTO(Question question){
		QuestionDTO dto = new QuestionDTO();
		dto.setOriginalImage(question.getOriginalImage());
		dto.setId(question.getOriginalImage());
		dto.setContent(question.getContent());
		dto.setContentImage(question.getContentImage());
		dto.setRightTimes(question.getRightTimes());
		dto.setWrongTimes(question.getWrongTimes());
		dto.setKnowledgeTag(question.getKnowledgeTag());
		dto.setWrongRate(question.getWrongRate());
		dto.setDifficulty(question.getDifficulty());
		return dto;
	}
	
	@Override
	public Map<String,Integer> getWrongRateStatic(){
		if(questions == null){
			init();
		}	
		
		NumberFormat numberFormat = NumberFormat.getInstance();  // 创建一个数值格式化对象  
		numberFormat.setMaximumFractionDigits(2); // 设置精确到小数点后2位  
		
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
	public Map<String, Integer> getKnowledgeTagStatics() {
		if(questions == null){
			init();
		}
		
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
		if(questions == null){
			init();
		}

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
	
	private Integer getInteger(String str){
		try{
			return Integer.parseInt(str);
		}catch(Exception e){
			return null;
		}
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


}
