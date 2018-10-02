package com.example.samplespringbatch;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.samplespringbatch.model.Person;
import com.example.samplespringbatch.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonCrudRepositoryTest {

	@Autowired
	private PersonService personService;

//	@Test
//	public void basicConfigurationTest() {
//		personService.deleteAll();
//		assertEquals(personService.countAll(), 0);
//
//		personService.save(new Person("", "Razvan", "Sebastian"));
//		assertEquals(personService.countAll(), 1);
//	}
//
//	@Test
//	public void test() {
//		personService.deleteAll();
//		assertEquals(personService.countAll(), 0);
//		
//		Person p1 = new Person("", "nam1", "n1");
//		Person p2 = new Person("", "nam2", "n2");
//		
//		List<Person> persons = new ArrayList<>();
//		persons.add(p1);
//		persons.add(p2);
//		
//		for (Person person : persons) {
//			personService.save(person);
//		}
//		
//		assertEquals(personService.countAll(), 2);
//	}

	@Test
	public void test2() {
		List<Person> list = personService.findAllWihtLastnameNot("Jane");
		for (Person person : list) {
			System.out.println(person);
		}
	}
}
