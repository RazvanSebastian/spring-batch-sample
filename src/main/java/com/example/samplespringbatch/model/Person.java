package com.example.samplespringbatch.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "person")
public class Person {

	@PrimaryKeyColumn(name = "register_date", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String registerDate;

	@PrimaryKeyColumn(name = "email", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String email;

	@PrimaryKeyColumn(name = "department", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	private String department;

	@Column(value = "firstname")
	private String firstName;

	@Column(value = "lastname")
	private String lastName;

	public Person() {
		super();
	}

	public Person(String registerDate, String email, String department, String firstName, String lastName) {
		super();
		this.registerDate = registerDate;
		this.email = email;
		this.department = department;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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
		return "Person [registerDate=" + registerDate + ", email=" + email + ", department=" + department
				+ ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
