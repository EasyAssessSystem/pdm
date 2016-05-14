package com.stardust.easyassess.pdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class EasyassessPdmApplication {

	public static void main(String[] args) {
		final SpringApplication app = new SpringApplication(EasyassessPdmApplication.class);
		app.run(args);

		//SpringApplication.run(EasyassessPdmApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean(RequestContextListener.class)
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}
