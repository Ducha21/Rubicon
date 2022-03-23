package vn.rts.customs.eclare.configuration.dbrouting;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vn.rts.customs.eclare.entity.MauToKhaiHqEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
		basePackages = "vn.rts.customs.eclare.repository.impl",
		transactionManagerRef = "transcationManager",
		entityManagerFactoryRef = "entityManager")
@EnableTransactionManagement
@Slf4j
public class DataSourceConfig {
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	Map<Object, Object> tenantDataSources = new HashMap<>();
	private AbstractRoutingDataSource multiTenantDataSource;

	@Bean
	@Primary
	@Autowired
	public DataSource dataSource() {
		multiTenantDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				return DataSourceContextHolder.getOrgCodeContext();
			}
		};
		tenantDataSources.put(userName, empDataSource());
		multiTenantDataSource.setTargetDataSources(tenantDataSources);
		multiTenantDataSource.setDefaultTargetDataSource(empDataSource());
		multiTenantDataSource.afterPropertiesSet();
		return multiTenantDataSource;
	}

	public DataSource empDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

	public void buildDataSource(String[] orgCodes) throws SQLException {
		for (String item : orgCodes) {
			DataSource dataSource = DataSourceBuilder.create()
					.driverClassName(driverClassName)
					.url(url)
					.username(item)
					.password(item)
					.build();

			// Check that new connection is 'live'. If not - throw exception
			try (Connection c = dataSource.getConnection()) {
				log.info("Init data source orgCode ["+item+"] routing finish.");
				tenantDataSources.put(item, dataSource);
				multiTenantDataSource.afterPropertiesSet();
			}
		}
	}

	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages(MauToKhaiHqEntity.class)
				.build();
	}

	@Bean(name = "transcationManager")
	public JpaTransactionManager transactionManager(
			@Autowired @Qualifier("entityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}