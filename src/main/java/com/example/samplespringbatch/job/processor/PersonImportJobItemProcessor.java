package com.example.samplespringbatch.job.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.dto.PersonDTO;
import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.util.DateBasicUtil;

@Component
public class PersonImportJobItemProcessor implements ItemProcessor<PersonDTO, Person>, InitializingBean {

	private DateBasicUtil dateBasicUtil;

	@Override
	public Person process(PersonDTO item) throws Exception {
		return new Person(dateBasicUtil.generateRegisterDate(), item.getFirstName(), item.getLastName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Autowired
	public void setDateBasicUtil(DateBasicUtil dateBasicUtil) {
		this.dateBasicUtil = dateBasicUtil;
	}

}
