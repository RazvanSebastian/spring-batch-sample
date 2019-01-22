package com.example.samplespringbatch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.samplespringbatch.model.Project;
import com.example.samplespringbatch.model.Status;
import com.example.samplespringbatch.repository.IProjectRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectRepositoryTest {
	
	@Autowired
	private IProjectRepository projectRepository;
	
	@Test
	public void test() {
		List<Project> result;
		try {
			result = projectRepository.findByNameAndType("project3","front");
			for (Project project : result) {
				System.out.println(project);
				
			}
			assertFalse(result.isEmpty());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	
	}
}
