package com.example.samplespringbatch.repository;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.samplespringbatch.model.Person;

@Repository
public interface IPersonRepository extends CrudRepository<Person, MapId> {
	
}
