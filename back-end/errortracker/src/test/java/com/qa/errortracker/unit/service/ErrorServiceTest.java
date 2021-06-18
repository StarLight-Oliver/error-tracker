package com.qa.errortracker.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.errortracker.domain.DeveloperDTO;
import com.qa.errortracker.domain.GluaError;
import com.qa.errortracker.domain.GluaErrorDTO;
import com.qa.errortracker.repo.ErrorRepo;
import com.qa.errortracker.service.ErrorService;

@SpringBootTest
public class ErrorServiceTest {
	@Autowired
	private ErrorService service;
	
	@MockBean
	private ErrorRepo repo;
	
	@Test 
	void testCreate() {
		String hash = "abc";
		String name = "Basic Error";
		String shortErr = "Short Part";
		String stack = "asdasdasdasd#asdasdasd";
		int count = 1;
		Short state = 1;
		
		GluaError existing = new GluaError();
		existing.setHash(hash);
		existing.setName(name);
		existing.setShortErr(shortErr);
		existing.setStack(stack);		
		existing.setCount(count);
		existing.setState(state);
		
		GluaErrorDTO returning = new GluaErrorDTO();
		returning.setHash(hash);
		returning.setName(name);
		returning.setShortErr(shortErr);
		returning.setStack(stack);		
		returning.setCount(count);
		returning.setState(state);
		returning.setDevelopers(new ArrayList<DeveloperDTO>());
		
		Mockito.when(this.repo.save(existing)).thenReturn(existing);
		
		GluaErrorDTO a = this.service.create(returning);
		
		assertThat(a).isEqualTo(returning);
		
		Mockito.verify(this.repo, Mockito.times(1)).save(existing);
	
	}
}
