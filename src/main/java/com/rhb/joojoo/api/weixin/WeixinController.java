package com.rhb.joojoo.api.weixin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhb.joojoo.service.QuestionSevice;
import com.rhb.joojoo.util.ImageTool;


@RestController
public class WeixinController {
	@Autowired
	QuestionSevice questionService;

	@Value("${rootPath}")
	private String rootPath;
	
	private final static String imagePath = "images/";
	
	@Value("${httpPath}")
	private String httpPath;
	
	@GetMapping("weixin/")
	public String check(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr){
		
		System.out.println("signature:" + signature);
		System.out.println("timestamp:" + timestamp);
		System.out.println("nonce:" + nonce);
		System.out.println("echostr:" + echostr);
		
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			System.out.println("check success!");
			return echostr;
		}else{
			System.out.println("check ERROR!");
			return null;
		}
	}
	
	@PostMapping("weixin/")
	public void getMessage(HttpServletRequest request,HttpServletResponse response){
	    response.setCharacterEncoding("utf-8");  
	    
	    try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  

		//System.out.println("recieved a message!");
		Map<String,String> map = MessageUtil.xmlToMap(request);
		String toUserName = map.get("ToUserName");
		String fromUserName = map.get("FromUserName");
		String msgType = map.get("MsgType");
		String content = map.get("Content");
		String picUrl = map.get("PicUrl");
		String mediaId = map.get("MediaId");
		
		//System.out.println("picUrl: " + picUrl);
		//System.out.println("mediaId: " + mediaId);
		
		if("image".equals(msgType)){
	    	String picpath = rootPath.substring(6) + this.imagePath;
	    	File file = HttpConnectionUtil.downloadFile(picUrl, picpath);
			try{
				ImageTool.scale2(file.getPath(), file.getPath(), 768, 1024, false);
			}catch(Exception e){
			}

	    	if(file != null && !file.getName().isEmpty()){
				questionService.addImage(file.getName());
	    	}
		}
		TextMessage tm = new TextMessage();
		tm.setToUserName(fromUserName);
		tm.setFromUserName(toUserName);
		tm.setCreateTime(new Date().getTime());
		tm.setMsgType("text");
		tm.setContent(getFace());
		String msg = MessageUtil.messageToXml(tm);
		
		PrintWriter out = null;  
		try {  
	        out = response.getWriter();  
	        out.write(msg);  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }finally{
		    out.close(); 
	    }
	}
	
	private String getFace(){
		String faces="/::) /::~ /::B /::| /:8-) /::< /::$ /::X /::Z /::’( /::-| /::@ /::P /::D /::O /::( /::+ /:–b /::Q /::T /:,@P /:,@-D /::d /:,@o /::g /:|-) /::! /::L /::> /::,@ /:,@f /::-S /:? /:,@x /:,@@ /::8 /:,@! /:!!! /:xx /:bye /:wipe /:dig /:handclap /:&-( /:B-) /:<@ /:@> /::-O /:>-| /:P-( /::’| /:X-) /::* /:@x /:8* /:pd /:<W> /:beer /:basketb /:oo /:coffee /:eat /:pig /:rose /:fade /:showlove /:heart /:break /:cake /:li";
		String words = "微笑 我好伤心 你是美女吗 让我发会呆 装个酷 让我哭会 我有点羞哟 不想说你了 睡觉去了 你让我泪如雨下 我好囧 我生气了 调皮一下 笑死我了 惊讶 我好难过 我酷吗 汗 抓狂 吐 笑 快乐 奇 傲 饿 累 吓 汗 高兴 闲 努力 骂 疑问 秘密 乱 疯 哀 鬼 打击 bye 汗 抠 鼓掌 糟糕 恶搞 什么 什么 累 看 难过 难过 坏 亲一口 吓你一下 可怜 刀 送你水果 送你酒 你该打篮球 你该打乒乓 来一杯咖啡 送你美食 爱动物吗？ 送你鲜花 枯 唇 好爱你 你一点也不爱我，分手吧 今天是我生日哟 电你一下";
		String[] fs = faces.split(" ");
		String[] ds = words.split(" ");
		int i = (int)(Math.random() * fs.length);
		
		return ds[i] + " " + fs[i];
		
	}


}
