package dev.fringe;

import java.util.Set;
import java.util.UUID;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JobRunSupport {
	
	@Autowired JobLauncher jobLauncher; @Autowired JobExplorer jobExplorer; @Autowired ApplicationContext context;
	@SneakyThrows public void run(String jobName) {
		Set<JobExecution> jobExecutions = jobExplorer.findRunningJobExecutions(jobName);
		if(jobExecutions.size() > 0 ) {
			log.warn("jobName = "+ jobName +" is still running finish this job" );
			System.exit(1);
		}
		JobExecution execution = jobLauncher.run(context.getBean(jobName, Job.class), new JobParametersBuilder().addString("job",jobName).addString("uid", UUID.randomUUID().toString()).toJobParameters());
		if (execution.getStatus() != BatchStatus.COMPLETED) {
			log.info("finish - error");
			System.exit(1);
		}
		log.info("finish");
	}
}
