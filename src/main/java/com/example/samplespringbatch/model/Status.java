package com.example.samplespringbatch.model;

public enum Status {
	SCHEMA("SCHEMA"), PROGRESS("PROGRESS"), COMPLETED("COMPLETED");

	private String text;

	Status(String text) {
		this.text = text;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return text;
	}

}
