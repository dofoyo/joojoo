package com.rhb.joojoo.domain;

import java.util.Date;
import java.util.UUID;

public class Practice {
	private String id;
	private Date madeDate;
	private boolean flag;  //正确为true；错误为false
	private String reasonTag; //错误原因标签（审题、计算、答非所问、不会）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getReasonTag() {
		return reasonTag;
	}
	public void setReasonTag(String reasonTag) {
		this.reasonTag = reasonTag;
	}
	
	

}
