package com.example.samplespringbatch.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.samplespringbatch.exception.JobNotFoundException;
import com.example.samplespringbatch.util.JobFactory;
import com.example.samplespringbatch.util.JobLunchHandler;
import com.example.samplespringbatch.util.JobName;

@RestController("/job-luncher")
public class JobLuncherController {

	@Autowired
	private JobLunchHandler jobLunchHandler;

	@Autowired
	private JobFactory jobFactory;
	
	@Autowired
	private JobExplorer jobs;

	@GetMapping("/lunch")
	@ResponseStatus(code = HttpStatus.OK)
	public String lunchJob(@RequestParam("job-name") JobName jobName) throws JobNotFoundException {
		JobExecution execution;
		try {
			execution = jobLunchHandler.lunch(jobFactory.getJob(jobName));
			StringBuilder builder = new StringBuilder();
			builder
				.append("Job name : ").append(execution.getJobInstance().getJobName()).append("\n")
				.append("Job instance id : ").append(execution.getJobInstance().getInstanceId()).append("\n")
				.append("JobExecution id : ").append(execution.getId()).append("\n")
				.append("Create time : ").append(execution.getCreateTime()).append("(\n");
			
			return builder.toString();
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@GetMapping(produces ="text/html" ,value = "/execution-status/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public String getStatus(@PathVariable("id") Long id) {
		JobExecution execution = jobs.getJobExecution(id);
		
		StringBuilder builder = new StringBuilder();
		builder
			.append("Job name : ").append(execution.getJobInstance().getJobName()).append("\n")
			.append("Job instance id : ").append(execution.getJobInstance().getInstanceId()).append("\n")
			.append("JobExecution id : ").append(execution.getId()).append("\n")
			.append("Create time : ").append(execution.getCreateTime()).append("\n")
			.append("End time : ").append(execution.getEndTime()).append("\n")
			.append("Status : ").append(execution.getExitStatus().getExitCode()).append("\n");
		
		return builder.toString();
	}

	@GetMapping(produces = "text/html", value = "/instances/{job-name}")
	@ResponseStatus(code = HttpStatus.OK)
	public String getJobInstances(@PathVariable("job-name") JobName  jobName) throws JobNotFoundException {
		List<JobInstance> lastInstances = jobs.getJobInstances(jobFactory.getJob(jobName).getName(), 0, 1);

		StringBuilder builder = new StringBuilder();
		builder.append("Job name : ").append(jobName).append("\n");
		for (JobInstance jobInstance : lastInstances) {
			builder.append("Instance id : ").append(jobInstance.getInstanceId()).append("\n");
		}
		return builder.toString();
	}

	@GetMapping(produces = "text/html", value = "/executions")
	@ResponseStatus(code = HttpStatus.OK)
	public String getInstanceExecutions(@RequestParam("job-instance") Long instanceId, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		List<JobExecution> lastInstances = jobs.getJobExecutions(jobs.getJobInstance(instanceId));
		StringBuilder builder = new StringBuilder();
		builder
			.append("######################################").append("\n")
			.append("Job name : ").append(jobs.getJobInstance(instanceId).getJobName()).append("\n")
			.append("Job instance : ").append(instanceId).append("\n")
			.append("######################################").append("\n\n\n");
		
		for (JobExecution jobExecution : lastInstances) {
				builder.append("#########################").append("\n")
					.append("Execution id : ").append(jobExecution.getId()).append("\n")
					.append("Create time : ").append(jobExecution.getCreateTime()).append("\n")
					.append("End time : ").append(jobExecution.getEndTime()).append("\n")
					.append("Status : ").append(jobExecution.getExitStatus().getExitCode()).append("\n");
		}

		return builder.toString();
	}

}
