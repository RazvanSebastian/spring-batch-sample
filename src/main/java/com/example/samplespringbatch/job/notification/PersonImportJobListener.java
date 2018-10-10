package com.example.samplespringbatch.job.notification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.service.PersonService;

@Component
public class PersonImportJobListener extends JobExecutionListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonImportJobListener.class);

	@Autowired
	private PersonService personService;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOGGER.info("Job with instance " + jobExecution.getJobInstance() + " created at "
				+ jobExecution.getCreateTime().toString());
		super.beforeJob(jobExecution);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		LOGGER.info("Job with instance " + jobExecution.getJobInstance() + " ended at "
				+ jobExecution.getCreateTime().toString() + " with following result");
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			List<Person> list = personService.findAll();
			for (Person person : list) {
				System.out.println(person);
			}
		}
	}

}
