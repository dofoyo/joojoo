package com.rhb.joojoo.util;

import java.io.File;

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
				FileUtil.renameFile(path, oldname, newname);
				//System.out.println(oldname + " --> "+ newname);
			}
		}
	}
	
	@Test
	public void testMove(){
		String file = "D:\\mydocs\\ranluwei\\joojoo\\todo\\joojoo.pptx";
		String destDir = "D:\\mydocs\\ranluwei\\joojoo\\content";
		boolean flag = FileUtil.move(file, destDir);
		if(flag){
			System.out.println("移动成功！");
		}else{
			System.out.println("失败！！！");
		}
	}
}
