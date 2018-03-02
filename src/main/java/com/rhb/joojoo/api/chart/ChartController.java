package com.rhb.joojoo.api.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhb.joojoo.api.ResponseContent;
import com.rhb.joojoo.api.ResponseEnum;
import com.rhb.joojoo.service.QuestionSevice;

@RestController
public class ChartController{
	@Autowired
	QuestionSevice questionService;
	
	
	@GetMapping("/difficultyChart")
	public ResponseContent<List<ChartDTO>> getDifficulty(@RequestParam(value="wrongRateFilter", defaultValue="") String wrongRateFilter) {
		//System.out.println(wrongRateFilter);
		Map<String,Integer> tags = questionService.getDifficulty(wrongRateFilter);
		List<ChartDTO> list = new ArrayList<ChartDTO>();
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			list.add(new ChartDTO(entry.getKey(), entry.getValue()));
			//System.out.println(entry.getKey()+ ": " + entry.getValue());
		}
		
		Collections.sort(list, new Comparator<ChartDTO>(){
			public int compare(ChartDTO dto1, ChartDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		return new ResponseContent<List<ChartDTO>>(ResponseEnum.SUCCESS,list);
	}
	

	
	@GetMapping("/wrongRateChart")
	public ResponseContent<List<ChartDTO>> getWrongRate(){
		Map<String,Integer> tags = questionService.getWrongRateStatic();
		List<ChartDTO> list = new ArrayList<ChartDTO>();
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			list.add(new ChartDTO(entry.getKey(), entry.getValue()));
			//System.out.println(entry.getKey()+ ": " + entry.getValue());
		}
		
		Collections.sort(list, new Comparator<ChartDTO>(){
			public int compare(ChartDTO dto1, ChartDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		return new ResponseContent<List<ChartDTO>>(ResponseEnum.SUCCESS,list);
	}	
	
	
	@GetMapping("/wrongTagChart")
	public ResponseContent<List<ChartDTO>> getWrongTags(@RequestParam(value="threshold", defaultValue="1") String threshold) {
		Map<String,Integer> tags = questionService.getWrongTagStatics();
		List<ChartDTO> list = new ArrayList<ChartDTO>();
		int total = 0;
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			total += entry.getValue();
			list.add(new ChartDTO(entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(list, new Comparator<ChartDTO>(){
			public int compare(ChartDTO dto1, ChartDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		Float f = Float.parseFloat(threshold);
		
		List<ChartDTO> list2 = new ArrayList<ChartDTO>();
		int eighty = 0;
		int other = 0;
		for(ChartDTO dto : list){
			eighty += dto.getValue();
			if((float)eighty/(float)total > f){
				other += dto.getValue();
			}else{
				list2.add(new ChartDTO(dto.getName(), dto.getValue()));
			}
		}
		if(f.intValue() < 1){
			list2.add(new ChartDTO("other", other));
		}
		
		return new ResponseContent<List<ChartDTO>>(ResponseEnum.SUCCESS,list2);
	}
	
	@GetMapping("/knowledgeTagChart")
	public ResponseContent<List<ChartDTO>> getKnowledgeTags(@RequestParam(value="threshold", defaultValue="1") String threshold) {
		Map<String,Integer> tags = questionService.getKnowledgeTagStatics();
		List<ChartDTO> list = new ArrayList<ChartDTO>();
		int total = 0;
		for(Map.Entry<String, Integer> entry : tags.entrySet()){
			total += entry.getValue();
			list.add(new ChartDTO(entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(list, new Comparator<ChartDTO>(){
			public int compare(ChartDTO dto1, ChartDTO dto2){
				return dto2.getValue().compareTo(dto1.getValue());
			}
		});
		
		Float f = Float.parseFloat(threshold);
	
		List<ChartDTO> list2 = new ArrayList<ChartDTO>();
		int eighty = 0;
		int other = 0;
		for(ChartDTO dto : list){
			eighty += dto.getValue();
			if((float)eighty/(float)total > f){
				other += dto.getValue();
			}else{
				list2.add(new ChartDTO(dto.getName(), dto.getValue()));
			}
		}
		if(f.intValue() < 1){
			list2.add(new ChartDTO("other", other));
		}
		
		return new ResponseContent<List<ChartDTO>>(ResponseEnum.SUCCESS,list2);
	}	
}
