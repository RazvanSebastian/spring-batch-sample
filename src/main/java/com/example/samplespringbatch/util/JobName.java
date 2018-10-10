package com.example.samplespringbatch.util;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum JobName {

	PERSON_IMPORT("PERSON_IMPORT"), PROJECT_IMPORT("PROJECT_IMPORT");

	private String text;

	JobName(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
