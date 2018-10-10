package com.example.samplespringbatch.model;

public enum Technology {
	JAVA("JAVA"), WEB("WEB"), CASSANDRA("CASSANDRA"), JSP("JSP"), PHP("PHP"),HTML("HTML"),CSS("CSS"),NET("NET"),ANGULAR("ANGULAR");

	private String text;

	Technology(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
