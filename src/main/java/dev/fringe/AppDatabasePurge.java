package dev.fringe;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StopWatch;

import dev.fringe.config.CommonConfig;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Import(CommonConfig.class)
public class AppDatabasePurge {

	@Autowired DataSource dataSource;
	
	public static void main(String[] args){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("database init");
		log.info("start");
		new AnnotationConfigApplicationContext(AppDatabasePurge.class);
		stopWatch.stop();
		log.info("total spend = " + stopWatch.getTotalTimeSeconds() + " sec");
	}

	@Bean
	public DataSourceInitializer initDB() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScripts(new ClassPathResource("org/springframework/batch/core/schema-drop-mysql.sql"), new ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));
        DataSourceInitializer init = new DataSourceInitializer();
        init.setDataSource(dataSource);
        init.setDatabasePopulator(populator);
        return init;
	}
	
}
