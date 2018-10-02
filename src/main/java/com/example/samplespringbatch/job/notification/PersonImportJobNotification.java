package com.example.samplespringbatch.job.notification;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.service.PersonService;

@Component
public class PersonImportJobNotification extends JobExecutionListenerSupport {

	@Autowired
	private PersonService personService;

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			List<Person> list = personService.findAll();
			for (Person person : list) {
				System.out.println(person);
			}

		}
	}

}
