package com.scy.driving.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
	@Autowired
	private Environment env;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 配置本地资源映射路径
		registry.addResourceHandler("/avatar/**").addResourceLocations("file:" + env.getProperty("upload.avatar.location"));
	}
}
