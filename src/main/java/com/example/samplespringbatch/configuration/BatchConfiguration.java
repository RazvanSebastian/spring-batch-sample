package com.example.samplespringbatch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

	@Autowired
	private JobRepository jobRepository;

	@Override
	public void setDataSource(DataSource dataSource) {
	}

	@Bean
	public TaskExecutor asyncTskExecutor() {
		return new SimpleAsyncTaskExecutor("spring_batch_async_task_executor");
	}

	@Bean
	public JobLauncher personImportJobAsyncLauncher(TaskExecutor asyncTskExecutor) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(asyncTskExecutor);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
	
	@Bean
	public JobLauncher projectImportJobAsyncLauncher(TaskExecutor asyncTskExecutor) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(asyncTskExecutor);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

}
