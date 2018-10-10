package com.example.samplespringbatch.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledPersonImportJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledPersonImportJob.class);
	private static final String JOB1_NAME = "JOB1";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobOperator operator;

	@Autowired
	private JobExplorer jobs;

	@Autowired
	private Job personImportJob;

	@Scheduled(fixedRate = 5000)
	public void runJob1() throws Exception {
		List<JobInstance> lastInstances = jobs.getJobInstances(JOB1_NAME, 0, 1);
		JobExecution execution = null;
		if (lastInstances.isEmpty()) {
			execution = jobLauncher.run(personImportJob, new JobParameters());
			loggExecutionResult(execution);
		} else {
			Long executionId = operator.startNextInstance(JOB1_NAME);
			execution = jobs.getJobExecution(executionId);
			loggExecutionResult(execution);
		}

	}

	private void loggExecutionResult(JobExecution execution) {
		LOGGER.info(execution.getJobInstance().getJobName());
		LOGGER.info(execution.getCreateTime().toString());
		LOGGER.info(execution.getEndTime().toString());
		LOGGER.info(execution.getStatus().name());
	}

}
