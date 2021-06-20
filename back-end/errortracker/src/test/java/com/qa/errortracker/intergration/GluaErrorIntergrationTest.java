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
import com.qa.errortracker.domain.GluaError;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:errortracker-schema.sql",
		"classpath:errortracker-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class GluaErrorIntergrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void testCreate() throws Exception {
		GluaError err = new GluaError();
		err.setHash("abc");
		err.setStack("cde");
		err.setShortErr("fgh");
		err.setName("Aasdasd");
		
		String errJson = this.mapper.writeValueAsString(err);
		
		this.mvc.perform(post("/api/error/create").content(errJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(errJson));
	}
	
	@Test
	void testGetAll() throws Exception { // need to move to a Before test
		GluaError err = new GluaError();
		err.setHash("-1802969628");
		err.setStack("asdasdasdasdasdasd");
		err.setShortErr("gamemodes/starwarsrp/gamemode/modules/lightsaber_system/weapons/weapon_lightsaber_base/init.lua:271: attempt to index a nil value");
		err.setName("asdasdasd");
		err.setState( (short) 1);

		Developer dev = new Developer();
		dev.setId(1L);
		dev.setName("Alpha");
		
		List<Developer> listDevs = List.of(dev);

		err.setDevelopers(listDevs);

		List<GluaError> listErrs = List.of(err);
		
		String errsJson = this.mapper.writeValueAsString(listErrs);
		
		this.mvc.perform(get("/api/error/getAll")).andExpect(status().isOk()).andExpect(content().json(errsJson));
	}
	
	@Test
	void testGet() throws Exception {
		GluaError err = new GluaError();
		err.setHash("-1802969628");
		err.setStack("asdasdasdasdasdasd");
		err.setShortErr("gamemodes/starwarsrp/gamemode/modules/lightsaber_system/weapons/weapon_lightsaber_base/init.lua:271: attempt to index a nil value");
		err.setName("asdasdasd");
		err.setState( (short) 1);

		Developer dev = new Developer();
		dev.setId(1L);
		dev.setName("Alpha");
		
		List<Developer> listDevs = List.of(dev);

		err.setDevelopers(listDevs);
	
		
		String errJson = this.mapper.writeValueAsString(err);
		
		this.mvc.perform(get("/api/error/get/" + err.getHash())).andExpect(status().isOk()).andExpect(content().json(errJson));
	}
	
	@Test
	void testDelete() throws Exception {
		String hash = "-1802969628";

		this.mvc.perform(delete("/api/error/delete/" + hash)).andExpect(status().isOk()).andExpect(content().string("true"));
	}

	@Test
	void testUpdate() throws Exception {
		GluaError err = new GluaError();
		err.setHash("-1802969628");
		err.setStack("asdasdasdasdasdasd");
		err.setShortErr("gamemodes/starwarsrp/gamemode/modules/lightsaber_system/weapons/weapon_lightsaber_base/init.lua:271: attempt to index a nil value");
		err.setName("asdasdasd");
		err.setState( (short) 2);

		Developer dev = new Developer();
		dev.setId(1L);
		dev.setName("Alpha");
		
		List<Developer> listDevs = List.of(dev);

		err.setDevelopers(listDevs);
			
		String errJson = this.mapper.writeValueAsString(err);
		
		this.mvc.perform(put("/api/error/update/" + err.getHash()).content(errJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(errJson));
	}

}
