package com.example.samplespringbatch.job.processor;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.utils.UUIDs;
import com.example.samplespringbatch.dto.ProjectDTO;
import com.example.samplespringbatch.model.Project;
import com.example.samplespringbatch.model.Status;
import com.example.samplespringbatch.model.Technology;

@Component
public class ProjectImportJobItemProcessor implements ItemProcessor<ProjectDTO, Project> {

	@Override
	public Project process(ProjectDTO item) throws Exception {
		Project project = new Project();
		project.setName(item.getProjectName());
		project.setCreated(UUIDs.timeBased());
		project.setType(item.getProjectType());
		project.setStatus(Status.valueOf(item.getProjectStatus()));
		project.setTechnologies(Stream.of(item.getProjectTechnologies().split(";")).map(s -> {
			return Technology.valueOf(s);
		}).collect(Collectors.toCollection(HashSet::new)));

		return project;
	}

}
