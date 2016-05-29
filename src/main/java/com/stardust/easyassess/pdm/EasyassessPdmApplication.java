package com.stardust.easyassess.pdm;

import com.stardust.easyassess.pdm.common.AuthenticationProxy;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.request.RequestContextListener;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EasyassessPdmApplication {

	@Autowired
	AuthenticationProxy authenticationProxy;

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

	@PostConstruct
	public void init() {
		authenticationProxy.fetch();
	}

	@Bean
	@ConditionalOnMissingBean(RequestContextListener.class)
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}
