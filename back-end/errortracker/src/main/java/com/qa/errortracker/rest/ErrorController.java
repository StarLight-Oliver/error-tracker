package com.qa.errortracker.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qa.errortracker.domain.GluaErrorDTO;
import com.qa.errortracker.service.ErrorService;

@RestController
@RequestMapping("/api/error")
public class ErrorController {
	
	private ErrorService service;
	
	@Autowired
	public ErrorController (ErrorService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create")
	public GluaErrorDTO create(@RequestBody GluaErrorDTO err) {
		return this.service.create(err);
	}
		
	@GetMapping("/getAll")
	public List<GluaErrorDTO> getAll() {
		return this.service.getAll();
	}
	
	@GetMapping("/get/{hash}")
	public GluaErrorDTO get(@PathVariable String hash) {
		return this.service.get(hash);
	}
	
	
	@PutMapping("/update/{hash}")
	public GluaErrorDTO update(@RequestBody GluaErrorDTO err,  @PathVariable String hash) {
		return this.service.update(err, hash);
	}
	
	@DeleteMapping("/delete/{hash}")
	public boolean delete(@PathVariable String hash) {
		return this.service.delete(hash);
	}
}
