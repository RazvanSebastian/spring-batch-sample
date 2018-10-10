package com.example.samplespringbatch.repository;

import java.util.List;

import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.model.Project;
import com.example.samplespringbatch.model.Status;

@Component
public abstract class ProjectRepository implements IProjectRepository {

	private CassandraTemplate cassandraTemplate;

	public ProjectRepository(CassandraTemplate cassandraTemplate) {
		super();
		this.cassandraTemplate = cassandraTemplate;
	}

	@Override
	public List<Project> findByNameAndType(String name,String type) {
		Where select = QueryBuilder.select().from("project").where(QueryBuilder.eq("name", name)).and(QueryBuilder.eq("type", type));
		System.out.println(select.toString());
		return cassandraTemplate.select(select, Project.class);
	}

}
