package com.rhb.joojoo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FileUtilTest {

	//@Test
	public void testRename(){
		String path = "D:\\mydocs\\ranluwei\\joojoo\\wrong";
		File dir = new File(path);
		File[] files = dir.listFiles();
		String oldname,newname;
		for(File f : files){
			
			if(f.isFile()){
				oldname = f.getName();
				if(oldname.contains("joojooj_")){
					newname = oldname;
				}else if(oldname.contains("joojoo_")){
					newname = "joojooj_" + oldname.substring(7);
				}else{
					newname = "joojooj_" + oldname;
				}
				FileUtil.rename(path, oldname, newname);
				//System.out.println(oldname + " --> "+ newname);
			}
		}
	}
	
	@Test
	public void testFor(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<100; i++){
			list.add(i);
		}
		
		for(Integer i : list){
			System.out.println(i);
			if(i==50){
				break;
			}
		}
		
		
	}
	
}
