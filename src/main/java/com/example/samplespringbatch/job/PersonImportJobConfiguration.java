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

import com.example.samplespringbatch.dto.PersonDTO;
import com.example.samplespringbatch.job.notification.PersonImportJobListener;
import com.example.samplespringbatch.job.processor.PersonImportJobItemProcessor;
import com.example.samplespringbatch.job.writer.PersonImportJobItemWriter;
import com.example.samplespringbatch.model.Person;

@Configuration
public class PersonImportJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private PersonImportJobItemProcessor processor;

	@Autowired
	private PersonImportJobItemWriter writer;
	
	@Bean
	public Job personImportJob(JobRepository jobRepository, PersonImportJobListener notificationListener, Step personImportStep1) {
		return jobBuilderFactory.get("personImportJob")
				.repository(jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(notificationListener)
				.flow(personImportStep1)
				.end()
				.build();
	}

	@Bean
	protected Step personImportStep1(PlatformTransactionManager transactionManager,FlatFileItemReader<PersonDTO> personFileReader) {
		DefaultTransactionAttribute transactionAttribute = new DefaultTransactionAttribute();
		transactionAttribute.setPropagationBehavior(Propagation.REQUIRED.value());
		transactionAttribute.setIsolationLevel(Isolation.DEFAULT.value());
        transactionAttribute.setTimeout(30);
        
		return stepBuilderFactory.get("step")
				.transactionManager(transactionManager)
				.<PersonDTO, Person>chunk(2)
				.reader(personFileReader)
				.processor(processor)
				.writer(writer)
				.faultTolerant()
				.retryLimit(3)
				.retry(DeadlockLoserDataAccessException.class)
				.transactionAttribute(transactionAttribute)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<PersonDTO> personFileReader() {
		return new FlatFileItemReaderBuilder<PersonDTO>().name("personItemReader")
				.resource(new ClassPathResource("person.csv"))
				.delimited()
				.names(new String[] { "email","department","firstName", "lastName" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<PersonDTO>() {
					{
						setTargetType(PersonDTO.class);
					}
				})
				.saveState(true)
				.build();
	}

}
