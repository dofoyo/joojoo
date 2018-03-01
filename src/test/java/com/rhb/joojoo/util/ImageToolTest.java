package com.rhb.joojoo.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class ImageToolTest {

	//@Test
	public void test(){
		String source = "D:\\mydocs\\ranluwei\\joojoo\\00001.jpg";
		String result = "D:\\mydocs\\ranluwei\\joojoo\\00001.jpg";
		ImageTool.scale2(source, result, 600, 800, false);
		
	}
	
	//@Test
	public void test2(){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			Object result = engine.eval("2<4");
			System.out.println(result.getClass().getName());
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3(){
		boolean flag = false;
		String difficultyFilter = ">4";
		int difficulty = 4;
		
		String operator = "";
		if(difficultyFilter.indexOf("<") == -1 && difficultyFilter.indexOf("=") == -1 && difficultyFilter.indexOf(">") == -1){
			operator = "==";
		}
		
		String str = Integer.toString(difficulty) + operator + difficultyFilter;
		System.out.println(str);
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			flag = (Boolean)engine.eval(str);
			System.out.println(flag);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

	}
}
