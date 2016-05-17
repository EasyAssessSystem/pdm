package com.stardust.easyassess.pdm;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.apache.log4j.Logger;

@SpringBootApplication
public class EasyassessPdmApplication {

	static Logger logger = Logger.getLogger(EasyassessPdmApplication.class);

	static {
		try{
			String log4jPath = EasyassessPdmApplication.class.getClassLoader().getResource("").getPath()+"log4j.properties";
			PropertyConfigurator.configure(log4jPath);
		}catch (Exception e){
			logger.error(e.getMessage());
		}
	}

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
