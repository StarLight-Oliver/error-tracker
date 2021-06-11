package com.qa.errortracker.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qa.errortracker.domain.GluaError;
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
	public GluaErrorDTO create(@RequestBody GluaError err) {
		return this.service.create(err);
	}
		
}
