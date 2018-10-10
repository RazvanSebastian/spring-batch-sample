package com.example.samplespringbatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.samplespringbatch.exception.PersonNotFoundException;
import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonCrudRepositoryTest {

	@Autowired
	private PersonService personService;

	@Test
	public void saveTest() {
		personService.deleteAll();
		assertEquals(personService.countAll(), 0);

		Person person = new Person("", "razvan@fortech.com", "3.3", "Razvan", "Parautiu");
		personService.save(person);

		assertEquals(personService.countAll(), 1);
	}

	@Test
	public void findByKeyTest() {
		personService.deleteAll();
		assertEquals(personService.countAll(), 0);
		Person person = new Person("", "razvan@fortech.com", "3.3", "Razvan", "Parautiu");
		person = personService.save(person);
		assertEquals(personService.countAll(), 1);

		MapId primaryKey = BasicMapId.id("registerDate", person.getRegisterDate()).with("email", person.getEmail());
		try {
			Person p = personService.find(primaryKey);
			System.out.println(p.toString());
			assertNotEquals(p, null);
		} catch (PersonNotFoundException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
