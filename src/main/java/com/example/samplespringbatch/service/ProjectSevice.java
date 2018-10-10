package com.example.samplespringbatch.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.samplespringbatch.model.Project;
import com.example.samplespringbatch.model.Technology;
import com.example.samplespringbatch.repository.IProjectRepository;
import com.google.common.collect.Lists;

@Service
public class ProjectSevice {

	@Autowired
	private IProjectRepository projectRepository;

	public List<Project> findAll() {
		return Lists.newArrayList(projectRepository.findAll());
	}
	
	public List<Project> findByTechnologies(Set<Technology> technologies){
		return null;
	}
}
