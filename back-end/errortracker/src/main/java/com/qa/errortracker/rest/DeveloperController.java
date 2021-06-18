package com.qa.errortracker.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.errortracker.domain.DeveloperDTO;
import com.qa.errortracker.service.DeveloperService;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

	private DeveloperService service;
	
	public DeveloperController(DeveloperService service) {
		this.service = service;
	}
	
	@PostMapping("/create")
	public DeveloperDTO create(@RequestBody DeveloperDTO dev) {
		return this.service.create(dev);
	}
	
	@GetMapping("/getAll")
	public List<DeveloperDTO> getAll() {
		return this.service.getAll();
	}
	
	@GetMapping("/get/{id}")
	public DeveloperDTO get(@PathVariable Long id) {
		return this.service.get(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean delete(@PathVariable Long id) {
		return this.service.delete(id);
	}
	
}
