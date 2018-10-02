package com.example.samplespringbatch.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.samplespringbatch.model.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

	@Query("TRUNCATE Person")
	public void deleteAll();
	
	@Query("UPDATE Person SET firstName=:firstName, lastName=:lastName WHERE id = :registerDate")
	public void update(
			@Param("firstName") String firstName, 
			@Param("lastName") String lastName,
			@Param("registerDate") String registerDate);

	@Query("SELECT * FROM Person firstName!=?")
	public List<Person> findAllWithLastnameNot(String firstname);
}
