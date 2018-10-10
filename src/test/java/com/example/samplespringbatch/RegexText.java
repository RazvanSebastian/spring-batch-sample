package com.example.samplespringbatch;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;

public class RegexText {

	@Test
	public void test() {
		Where select = QueryBuilder.select().from("project").where(QueryBuilder.eq("name", "abc")).and(QueryBuilder.eq("status","abc"));
		System.out.println(select.toString());
		assertTrue(true);
	}

}
