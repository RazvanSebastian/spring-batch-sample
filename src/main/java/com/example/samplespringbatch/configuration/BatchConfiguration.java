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

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {
	
	@Autowired
	private JobRepository jobRepository;

	@Override
	public void setDataSource(DataSource dataSource) {
	}
	
	@Bean
	public JobLauncher jobAsyncLauncher() throws Exception {
	        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	        jobLauncher.setJobRepository(jobRepository);
	        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
	        jobLauncher.afterPropertiesSet();
	        return jobLauncher;
	}
	

}
