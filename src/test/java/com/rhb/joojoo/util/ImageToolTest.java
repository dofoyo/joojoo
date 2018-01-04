package com.rhb.joojoo.util;

import org.junit.Test;

public class ImageToolTest {

	@Test
	public void test(){
		String source = "D:\\mydocs\\ranluwei\\joojoo\\00001.jpg";
		String result = "D:\\mydocs\\ranluwei\\joojoo\\00001.jpg";
		ImageTool.scale2(source, result, 600, 800, false);
		
	}
}
