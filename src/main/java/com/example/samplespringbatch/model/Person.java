package com.example.samplespringbatch.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "person")
public class Person {

	@PrimaryKey("id")
	private String registerDate;

	@Column(value = "firstname")
	private String firstName;

	@Column(value = "lastname")
	private String lastName;

	public Person() {
		super();
	}

	public Person(String registerDate, String firstName, String lastName) {
		super();
		this.registerDate = registerDate;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [registerdate=" + registerDate + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
