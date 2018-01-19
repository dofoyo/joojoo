package com.rhb.joojoo;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.rhb.joojoo.service.QuestionSevice;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		QuestionSevice qs = event.getApplicationContext().getBean(QuestionSevice.class);
		qs.init();
	}

}
