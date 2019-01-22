package com.example.samplespringbatch.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.model.Project;
import com.example.samplespringbatch.model.Status;

@Repository
public interface IProjectRepository extends CrudRepository<Project, MapId> {

	public List<Project> findByNameAndType(String name, String type) throws InterruptedException, ExecutionException;
}
