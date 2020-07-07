package dev.fringe;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.StopWatch;

import dev.fringe.config.CommonConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Import(CommonConfig.class)@ImportResource("classpath:/app/job*.xml")@Log4j2 @Setter @Getter
public class App extends JobRunSupport{

	public static void main(String[] args){
		String jobName = args[0];
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(jobName);
		log.info("start");
		new AnnotationConfigApplicationContext(App.class).getBean(App.class).run(jobName);
		stopWatch.stop();
		log.info("total spend = " + stopWatch.getTotalTimeSeconds() + " sec");
	}
}
