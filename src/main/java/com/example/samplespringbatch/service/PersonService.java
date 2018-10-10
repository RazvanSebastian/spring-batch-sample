package com.example.samplespringbatch.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samplespringbatch.exception.PersonNotFoundException;
import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.repository.IPersonRepository;
import com.example.samplespringbatch.util.DateBasicUtil;

@Service
public class PersonService {

	@Autowired
	private IPersonRepository personRepository;

	@Autowired
	private DateBasicUtil dateBasicUtil;

	@Transactional
	public Person save(Person person) {
		person.setRegisterDate(dateBasicUtil.generateRegisterDate());
		person = personRepository.save(person);
		return person;
	}

	public List<Person> findAll() {
		List<Person> list = new ArrayList<>();
		Iterator<Person> iterator = personRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Person person = (Person) iterator.next();
			list.add(person);
		}

		return list;
	}

	public Person find(MapId primaryKey) throws PersonNotFoundException {
		return personRepository.findById(primaryKey).orElseThrow(
				() -> new PersonNotFoundException("Person with priamry key" + primaryKey.toString() + " not found"));
	}

	public void delete(Person person) {
		personRepository.delete(person);
	}

	public void deleteAll() {
		personRepository.deleteAll();
	}

	public long countAll() {
		return personRepository.count();
	}

}
