package com.example.samplespringbatch.util;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.exception.JobNotFoundException;

@Component
public class JobFactory {

	private Job personImportJob;
	private Job projectImportJob;

	public Job getJob(JobName jobName) throws JobNotFoundException {
		if (jobName == JobName.PERSON_IMPORT)
			return personImportJob;
		if (jobName == JobName.PROJECT_IMPORT)
			return projectImportJob;
		throw new JobNotFoundException("Job not found!");
	}

	@Autowired
	public void setPersonImportJob(Job personImportJob) {
		this.personImportJob = personImportJob;
	}

	@Autowired
	public void setProjectImportJob(Job projectImportJob) {
		this.projectImportJob = projectImportJob;
	}

}
