package com.example.samplespringbatch.util;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.exception.JobNotFoundException;

@Component
public class JobLaunchHandler {

	private JobLauncher personImportJobAsyncLauncher;
	private JobLauncher projectImportJobAsyncLauncher;

	public JobExecution launch(Job job) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobNotFoundException {
		if (job.getName().equals("projectImportJob"))
			return projectImportJobAsyncLauncher.run(job, new JobParameters());

		if (job.getName().equals("personImportJob"))
			return personImportJobAsyncLauncher.run(job, new JobParameters());

		throw new JobNotFoundException("Job not found!");
	}

	@Autowired
	public void setPersonImportJobAsyncLauncher(JobLauncher personImportJobAsyncLauncher) {
		this.personImportJobAsyncLauncher = personImportJobAsyncLauncher;
	}

	@Autowired
	public void setProjectImportJobAsyncLauncher(JobLauncher projectImportJobAsyncLauncher) {
		this.projectImportJobAsyncLauncher = projectImportJobAsyncLauncher;
	}

}
