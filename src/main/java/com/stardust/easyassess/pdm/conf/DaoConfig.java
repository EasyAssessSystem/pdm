package com.stardust.easyassess.pdm.conf;


import com.stardust.easyassess.pdm.dao.router.MultitenantDataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DaoConfig {

    @Autowired
    private DataSourceProperties properties;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws IOException {
        Map<Object,Object> resolvedDataSources = new HashMap<>();
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("classpath:/tenants/*.properties") ;

        for(Resource propertyFile : resources) {
            Properties tenantProperties = new Properties();
            //DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

            try {
                tenantProperties.load(propertyFile.getInputStream());

                String tenantId = tenantProperties.getProperty("name");

//                dataSourceBuilder.driverClassName(properties.getDriverClassName())
//                        .url(tenantProperties.getProperty("datasource.url"))
//                        .username(tenantProperties.getProperty("datasource.username"))
//                        .password(tenantProperties.getProperty("datasource.password"));
//
//                if(properties.getType() != null) {
//                    dataSourceBuilder.type(properties.getType());
//                }
//
//                resolvedDataSources.put(tenantId, dataSourceBuilder.build());
                  resolvedDataSources.put(tenantId,
                          createPooledDataSource(properties.getDriverClassName(),
                                                 tenantProperties.getProperty("datasource.url"),
                                                 tenantProperties.getProperty("datasource.username"),
                                                 tenantProperties.getProperty("datasource.password")));
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }
        }

        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setTargetDataSources(resolvedDataSources);

        // Call this to finalize the initialization of the data source.
        dataSource.afterPropertiesSet();
        return dataSource;
    }

    private DataSource defaultDataSource() {
//        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
//                .driverClassName(properties.getDriverClassName())
//                .url(properties.getUrl())
//                .username(properties.getUsername())
//                .password(properties.getPassword());
//        if(properties.getType() != null) {
//            dataSourceBuilder.type(properties.getType());
//        }
//
//        return dataSourceBuilder.build();
        return createPooledDataSource(properties.getDriverClassName(),
                properties.getUrl(),
                properties.getUsername(),
                properties.getPassword());
    }

    private org.apache.tomcat.jdbc.pool.DataSource createPooledDataSource(String driver,
                                                                          String url,
                                                                          String username,
                                                                          String password) {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(driver);
        poolProperties.setUrl(url);
        poolProperties.setUsername(username);
        poolProperties.setPassword(password);
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setMaxActive(100);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setTimeBetweenEvictionRunsMillis(20000);
        dataSource.setMinEvictableIdleTimeMillis(28700);
        return dataSource;
    }


    @Bean
    public EntityManagerFactory entityManagerFactory() throws IOException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.stardust.easyassess.pdm.models");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

}
