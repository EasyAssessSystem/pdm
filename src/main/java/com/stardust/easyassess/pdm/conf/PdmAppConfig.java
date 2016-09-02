package com.stardust.easyassess.pdm.conf;


import com.stardust.easyassess.core.context.ContextSession;
import com.stardust.easyassess.core.context.ShardedSession;
import com.stardust.easyassess.core.security.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
@Import({DaoConfig.class, ServiceConfig.class})
public class PdmAppConfig {

    @Value("${authentication.server}")
    private String authenticationServer;

    @Autowired
    @Scope("request")
    @Lazy
    @Bean
    public ContextSession getContextSession(HttpSession session, HttpServletRequest request) {
        Map pathVariables
                = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return new ShardedSession(session, (String) pathVariables.get("domain"));
    }

    @Bean(name="authenticationClient")
    @Lazy
    public AuthenticationClient getAuthenticationClient() {
        return new AuthenticationClient(authenticationServer);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("GET","PUT","POST","DELETE","HEAD","OPTIONS");
            }
        };
    }
}
