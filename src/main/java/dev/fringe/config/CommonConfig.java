package dev.fringe.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@PropertySource("classpath:app.properties")
@ComponentScan("dev.fringe.app.service")
@MapperScan("dev.fringe.app.mapper")
@Configuration
@Log4j2
public class CommonConfig{
	
	@EnableBatchProcessing
	public class BatchConfig{
		@Value("${app.jdbc.driver}") String driver;
		@Value("${app.jdbc.url}") String url;	
		@Value("${app.jdbc.user}") String user;	
		@Value("${app.jdbc.password}") String password;	
		
		@Bean
		public DataSource dataSource() {
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(password);
			return ds;
		}
	}

	@PostConstruct
	public void init() throws Exception {
		log.info("init");
	}

	@Bean @SneakyThrows
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactoryBean session = new SqlSessionFactoryBean();
		session.setDataSource(dataSource);
		session.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return session.getObject();
	}
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setBasePackage("dev.fringe.app.mapper");
        config.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return config;
    }	
}
