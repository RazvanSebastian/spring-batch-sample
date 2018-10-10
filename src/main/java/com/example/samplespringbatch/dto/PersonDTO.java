package com.example.samplespringbatch.dto;

public class PersonDTO {

	private String email;
	private String department;
	private String firstName;
	private String lastName;

	public PersonDTO() {
		super();
	}

	public PersonDTO(String email, String department, String firstName, String lastName) {
		super();
		this.email = email;
		this.department = department;
		this.firstName = firstName;
		this.lastName = lastName;
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

}
