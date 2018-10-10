package com.example.samplespringbatch.job.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.samplespringbatch.model.Project;
import com.example.samplespringbatch.repository.IProjectRepository;

@Component
public class ProjectImportJobItemWriter implements ItemWriter<Project> {

	@Autowired
	private IProjectRepository projectRepository;

	@Override
	public void write(List<? extends Project> items) throws Exception {
		for (Project project : items) {
			projectRepository.save(project);
		}
	}

}
