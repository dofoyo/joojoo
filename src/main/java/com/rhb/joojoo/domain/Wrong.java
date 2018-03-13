package com.rhb.joojoo.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Wrong {
	private String wrongImage;			//错题图片
	private String wrongTag; //错误原因标签（审题、计算、答非所问、不会）
	
	
	public String getWrongImage() {
		return wrongImage;
	}
	public void setWrongImage(String wrongImage) {
		this.wrongImage = wrongImage;
	}
	public String getWrongTag() {
		return wrongTag;
	}
	public void setWrongTag(String wrongTag) {
		this.wrongTag = wrongTag;
	}
	
	public long getDuration(){
		LocalDate madeDate = LocalDate.of(this.getYear(), this.getMonth(), this.getDay());
		LocalDate today = LocalDate.now();
		return ChronoUnit.DAYS.between(madeDate, today);
		 
	}
	
	private int getYear(){
		String madeDate = this.getMadeDateString();
		int year = 2017;
		if(madeDate!=null){
			year = Integer.parseInt(madeDate.substring(0,4));
		}
		return year;
	}

	private int getMonth(){
		String madeDate = this.getMadeDateString();
		int month = 1;
		if(madeDate!=null){
			month = Integer.parseInt(madeDate.substring(4,6));
		}
		return month;
	}

	private int getDay(){
		String madeDate = this.getMadeDateString();
		int day = 1;
		if(madeDate!=null){
			day = Integer.parseInt(madeDate.substring(6,8));
		}
		return day;
	}

	private String getMadeDateString(){
		String madeDate = null;
		if(this.wrongImage!=null){
			if(this.wrongImage.indexOf("joo")!=-1){
				madeDate = this.wrongImage.substring(8, 16);
			}else{
				madeDate = this.wrongImage.substring(0, 8);
			}
		}
		return madeDate;
	}
	
	public Integer getMadeDate() {
		Integer madeDate = 0;
		
		try{
			if(this.wrongImage!=null){
				if(this.wrongImage.indexOf("joo")!=-1){
					madeDate = getInteger(this.wrongImage.substring(8, 16));
				}else{
					madeDate = getInteger(this.wrongImage.substring(0, 8));
				}
			}
			
		}catch(Exception e){}
		
		return madeDate;
	}

	private Integer getInteger(String str){
		return Integer.parseInt(str);
	}
	
	
	
}
