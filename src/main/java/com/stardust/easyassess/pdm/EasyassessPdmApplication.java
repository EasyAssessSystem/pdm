package com.stardust.easyassess.pdm;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.request.RequestContextListener;
import org.apache.log4j.Logger;

@SpringBootApplication
public class EasyassessPdmApplication {

	static Logger logger = Logger.getLogger(EasyassessPdmApplication.class);

	static {
		try{
			ClassLoader cl = EasyassessPdmApplication.class.getClassLoader();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
			Resource[] resources = resolver.getResources("classpath:/log4j.properties") ;
			for(Resource log4j : resources) {
				PropertyConfigurator.configure(log4j.getURL());
			}
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
