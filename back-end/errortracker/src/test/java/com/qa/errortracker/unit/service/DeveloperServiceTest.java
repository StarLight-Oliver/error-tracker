package com.qa.errortracker.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.errortracker.domain.Developer;
import com.qa.errortracker.domain.DeveloperDTO;
import com.qa.errortracker.repo.DeveloperRepo;
import com.qa.errortracker.service.DeveloperService;


@SpringBootTest
class DeveloperServiceTest {
	@Autowired
	private DeveloperService service;
	
	@MockBean
	private DeveloperRepo repo;
	
	
	@Test 
	void testCreate() {
		
		Long Id = 1L;
		String name = "Ollie";

		Developer existing = new Developer();
		existing.setName(name);
		existing.setId(Id);

		DeveloperDTO returning = new DeveloperDTO();
		returning.setId(Id);
		returning.setName(name);

		Mockito.when(this.repo.save(existing)).thenReturn(existing);
		
		assertThat(this.service.create(returning)).isEqualTo(returning);
		
		Mockito.verify(this.repo, Mockito.times(1)).save(existing);
	}


	@Test
	void testGet() {

		Long Id = 1L;
		String name = "Ollie";
		Developer existing = new Developer();
		existing.setName(name);
		existing.setId(Id);

		
		DeveloperDTO returning = new DeveloperDTO();
		returning.setId(Id);
		returning.setName(name);
		
		Mockito.when(this.repo.findById(Id)).thenReturn(Optional.of(existing));
		
		assertThat(this.service.get(Id)).isEqualTo(returning);
		
		Mockito.verify(this.repo, Mockito.times(1)).findById(Id);
		
	}
	
	@Test
	void testGetAll() {

		Long Id = 1L;
		String name = "Ollie";
		Developer existing = new Developer();
		existing.setName(name);
		existing.setId(Id);


		DeveloperDTO returning = new DeveloperDTO();
		returning.setId(Id);
		returning.setName(name);

		List<Developer> devs = List.of(existing);
		List<DeveloperDTO> returnDevs = List.of(returning);

		Mockito.when(this.repo.findAll()).thenReturn(devs);
		
		assertThat(this.service.getAll()).isEqualTo(returnDevs);
		
		Mockito.verify(this.repo, Mockito.times(1)).findAll();
		
	}

	@Test
	void testDelete() {
		Long Id = 1L;
		boolean exists = false;

		Mockito.when(this.repo.existsById(Id)).thenReturn(exists);
		assertThat(this.service.delete(Id)).isEqualTo(!exists);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(Id);
	}
	@Test
	void testDeletFail() {
		Long Id = 1L;
		boolean exists = true;

		Mockito.when(this.repo.existsById(Id)).thenReturn(exists);
		assertThat(this.service.delete(Id)).isEqualTo(!exists);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(Id);
	}
}
