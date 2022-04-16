package com.projet.geopulserex.config.dbconfig;

import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "clouddenisEntityManagerFactory", basePackages = {
    "com.projet.geopulserex.repository.clouddenis" })
public class CloudDenisConfig {

    @Primary
    @Bean(name = "clouddenisDatasource")
	@ConfigurationProperties(prefix = "clouddenis.datasource")
	public DataSource dataSource() {
		System.out.println("clouddenis"+DataSourceBuilder.create().build());
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "clouddenisEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("clouddenisDatasource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		//properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return builder.dataSource(dataSource).properties(properties)
				.packages("com.projet.geopulserex.entity").persistenceUnit("Position").build();
	}

	
	@Bean(name = "clouddenisTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("clouddenisEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
    }
    
}
