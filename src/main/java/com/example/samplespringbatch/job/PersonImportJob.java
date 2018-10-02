package com.example.samplespringbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.samplespringbatch.dto.PersonDTO;
import com.example.samplespringbatch.job.notification.PersonImportJobNotification;
import com.example.samplespringbatch.job.processor.PersonImportJobItemProcessor;
import com.example.samplespringbatch.job.reader.FileItemsReader;
import com.example.samplespringbatch.job.writer.PersonImportJobItemWriter;
import com.example.samplespringbatch.model.Person;

@Configuration
public class PersonImportJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private PersonImportJobItemProcessor processor;

	@Autowired
	private PersonImportJobItemWriter writer;
	
	@Autowired
	private FileItemsReader reader;

	@Bean
	public Job job1(PersonImportJobNotification notificationListener, Step step) {
		return jobBuilderFactory.get("personImportJob")
				.incrementer(new RunIdIncrementer())
				.listener(notificationListener)
				.flow(step)
				.end()
				.build();
	}

	@Bean
	protected Step step() {
		return stepBuilderFactory.get("step")
				.<PersonDTO, Person>chunk(1)
				.reader(reader.personFileReader())
				.processor(processor)
				.writer(writer)
				.build();
	}



}
