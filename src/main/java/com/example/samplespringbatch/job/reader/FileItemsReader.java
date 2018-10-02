package com.example.samplespringbatch.job.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.dto.PersonDTO;

/**
 * Alternatives to saveState @see https://docs.spring.io/spring-batch/4.0.x/reference/html/readersAndWriters.html#process-indicator
 * 
 * @author razvan.parautiu
 *
 */

@Component
public class FileItemsReader {

	@Bean
	public FlatFileItemReader<PersonDTO> personFileReader() {
		return new FlatFileItemReaderBuilder<PersonDTO>().name("personItemReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names(new String[] { "firstName", "lastName" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<PersonDTO>() {
					{
						setTargetType(PersonDTO.class);
					}
				})
				.saveState(true)
				.build();
	}
}
