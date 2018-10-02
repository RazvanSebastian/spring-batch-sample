package com.example.samplespringbatch.job.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.repository.PersonRepository;

@Component
public class PersonImportJobItemWriter implements ItemWriter<Person> {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public void write(List<? extends Person> items) throws Exception {
		for (Person person : items) {
			personRepository.save(person);
		}

	}

}
