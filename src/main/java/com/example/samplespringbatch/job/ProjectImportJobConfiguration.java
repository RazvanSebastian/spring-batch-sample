package com.example.samplespringbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.example.samplespringbatch.dto.ProjectDTO;
import com.example.samplespringbatch.job.notification.ProjectImportJobListener;
import com.example.samplespringbatch.job.processor.ProjectImportJobItemProcessor;
import com.example.samplespringbatch.job.writer.ProjectImportJobItemWriter;
import com.example.samplespringbatch.model.Project;

@Configuration
public class ProjectImportJobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ProjectImportJobItemProcessor processor;
	
	@Autowired
	private ProjectImportJobItemWriter writer;
	
	@Bean
	public Job projectImportJob(JobRepository jobRepository, ProjectImportJobListener notificationListener,Step projectImportStep) {
		return jobBuilderFactory.get("projectImportJob")
				.repository(jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(notificationListener)
				.flow(projectImportStep)
				.end()
				.build();
	}
	
	@Bean
	protected Step projectImportStep(PlatformTransactionManager transactionManager, FlatFileItemReader<ProjectDTO> projectFileReader) {
		DefaultTransactionAttribute transactionAttribute = new DefaultTransactionAttribute();
		transactionAttribute.setPropagationBehavior(Propagation.REQUIRED.value());
		transactionAttribute.setIsolationLevel(Isolation.DEFAULT.value());
        transactionAttribute.setTimeout(30);
	
        return stepBuilderFactory.get("step")
				.transactionManager(transactionManager)
				.<ProjectDTO, Project>chunk(10)
				.reader(projectFileReader)
				.processor(processor)
				.writer(writer)
				.faultTolerant()
				.retryLimit(3)
				.retry(DeadlockLoserDataAccessException.class)
				.transactionAttribute(transactionAttribute)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<ProjectDTO> projectFileReader(){
		return new FlatFileItemReaderBuilder<ProjectDTO>().name("projectItemReader")
				.resource(new ClassPathResource("project.csv"))
				.delimited()
				.names(new String[] { "projectName", "projectType", "projectStatus", "projectTechnologies" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<ProjectDTO>() {
					{
						setTargetType(ProjectDTO.class);
					}
				})
				.saveState(true)
				.build();
	}
	
}
