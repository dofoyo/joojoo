package com.rhb.joojoo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; 
	
@Configuration  
public class CorsConfig extends WebMvcConfigurerAdapter {  

	@Value("${rootPath}")
	private String rootPath;
	
    @Override  
    public void addCorsMappings(CorsRegistry registry) {  
    	System.out.println("*********   CorsRegistry      ***************8");
    	
        registry.addMapping("/**")  
                .allowedOrigins("*")  
                .allowCredentials(true)  
                .allowedMethods("GET", "POST", "DELETE", "PUT")  
                .maxAge(3600);  
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {	
        registry.addResourceHandler("/originalImage/**").addResourceLocations(rootPath);
        registry.addResourceHandler("/contentImage/**").addResourceLocations(rootPath+"content/");
        registry.addResourceHandler("/wrongImage/**").addResourceLocations(rootPath+"wrong/");
        super.addResourceHandlers(registry);
    }
} 