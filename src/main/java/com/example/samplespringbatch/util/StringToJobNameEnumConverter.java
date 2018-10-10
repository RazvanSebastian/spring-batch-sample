package com.example.samplespringbatch.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToJobNameEnumConverter implements Converter<String, JobName>{

	@Override
	public JobName convert(String source) {
		return JobName.valueOf(source);
	}

}
