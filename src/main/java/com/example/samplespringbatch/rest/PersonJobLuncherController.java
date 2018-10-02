package com.example.samplespringbatch.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonJobLuncherController {

	@Autowired
	private JobLauncher jobAsyncLauncher;

	@Autowired
	private Job job1;

	@Autowired
	private JobExplorer jobs;

	@GetMapping("/jobLuncher")
	@ResponseStatus(code = HttpStatus.OK)
	public String handle() throws Exception {
		JobExecution execution = jobAsyncLauncher.run(job1, new JobParameters());
		return "JobExecution id = " + execution.getId();
	}

	@GetMapping(produces ="text/html" ,value = "/job-status/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public String getStatus(@PathVariable("id") Long id) {
		JobExecution execution = jobs.getJobExecution(id);
		StringBuilder builder = new StringBuilder();
		builder
			.append("<h3> Create time : ").append(execution.getCreateTime()).append("</h3>")
			.append("<h3> End time : ").append(execution.getEndTime()).append("</h3>")
			.append("<h3> Status : ").append(execution.getExitStatus().getExitCode()).append("</h3>");
		return builder.toString();
	}

	@GetMapping(produces = "text/html", value = "/instances")
	@ResponseStatus(code = HttpStatus.OK)
	public String getJobInstances(@RequestParam("job") String jobName) {
		List<JobInstance> lastInstances = jobs.getJobInstances(job1.getName(), 0, 1);

		StringBuilder builder = new StringBuilder();
		builder.append("<ul>");
		for (JobInstance jobInstance : lastInstances) {
			builder.append("<li>").append(jobInstance.getInstanceId()).append("</li>");
		}
		builder.append("</ul>");
		return builder.toString();
	}

	@GetMapping(produces = "text/html", value = "/executions")
	@ResponseStatus(code = HttpStatus.OK)
	public String getInstanceExecutions(@RequestParam("job-instance") Long instanceId, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		List<JobExecution> lastInstances = jobs.getJobExecutions(jobs.getJobInstance(instanceId));

		StringBuilder builder = new StringBuilder();
		builder.append("<ul>");
		for (JobExecution jobExecution : lastInstances) {
			builder.append("<ol>").append("Id:").append(jobExecution.getId()).append("<li> Create time:")
					.append(jobExecution.getCreateTime()).append("</li>").append("<li> End time:")
					.append(jobExecution.getEndTime()).append("</li>").append("<li> End time:")
					.append(jobExecution.getExitStatus().getExitCode()).append("</li>").append("</ol>");
		}
		builder.append("</ul>");
		return builder.toString();
	}

}
