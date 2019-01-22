package com.example.samplespringbatch.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.AsyncCassandraTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.concurrent.ListenableFuture;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.example.samplespringbatch.model.Project;

@Component
public abstract class ProjectRepository implements IProjectRepository {

	private CassandraTemplate cassandraTemplate;
	private AsyncCassandraTemplate asyncCassandraTemplate;

	@Override
	public List<Project> findByNameAndType(String name, String type) throws InterruptedException, ExecutionException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Where select = QueryBuilder.select().from("project").where(QueryBuilder.eq("name", name))
				.and(QueryBuilder.eq("type", type));
		ListenableFuture<List<Project>> resultFuture = asyncCassandraTemplate.select(select, Project.class);
		return resultFuture.get();
	}
	

	@Autowired
	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

	@Autowired
	public void setAsyncCassandraTemplate(AsyncCassandraTemplate asyncCassandraTemplate) {
		this.asyncCassandraTemplate = asyncCassandraTemplate;
	}

}
