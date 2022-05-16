package com.rr.springboot.scheduleinterceptor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	@Qualifier("scheduleInterceptor")
	private HandlerInterceptor schedule;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*
		 * Como el interceptor se aplica a todos los controladores del proyecto, si no
		 * excluimos "closed", creará un loop infinito
		 */
		registry.addInterceptor(schedule).excludePathPatterns("/closed");
	}
}
