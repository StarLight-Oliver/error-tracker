package com.qa.errortracker.intergration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.errortracker.domain.Developer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:errortracker-schema.sql",
		"classpath:errortracker-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class DeveloperIntegrationTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void testCreate() throws Exception {
		Developer dev = new Developer();
		dev.setName("Sam");
		
		String devJson = this.mapper.writeValueAsString(dev);
		dev.setId(2L);
		
		String expectJson = this.mapper.writeValueAsString(dev);
		
		this.mvc.perform(post("/api/developer/create").content(devJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(expectJson));
	}
	@Test
	void testGetAll() throws Exception { // need to move to a Before test
		Developer dev = new Developer();
		dev.setId(1L);
		dev.setName("Alpha");
		
		List<Developer> listDevs = List.of(dev);
		
		String devsJson = this.mapper.writeValueAsString(listDevs);
		
		this.mvc.perform(get("/api/developer/getAll")).andExpect(status().isOk()).andExpect(content().json(devsJson));
	}
	@Test
	void testGet() throws Exception {
		Developer dev = new Developer();
		dev.setId(1L);
		dev.setName("Alpha");
		
		String devJson = this.mapper.writeValueAsString(dev);
		
		this.mvc.perform(get("/api/developer/get/1")).andExpect(status().isOk()).andExpect(content().json(devJson));
		
	}
	
	@Test 
	void testUpdate() throws Exception {
		Developer dev = new Developer();
		dev.setId(1L);
		dev.setName("Beta");
		String devJson = this.mapper.writeValueAsString(dev);
		
		this.mvc.perform(put("/api/developer/update/1").content(devJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(devJson));
	}
	
	@Test
	void testDelete() throws Exception {
		String id = "1";

		//this.mvc.perform(delete("/api/developer/delete/" + id)).andExpect(status().isOk()).andExpect(content().string("true"));
	}
}
