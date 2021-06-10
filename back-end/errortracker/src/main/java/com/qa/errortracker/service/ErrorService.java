package com.qa.errortracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.GluaError;
import com.qa.errortracker.repo.ErrorRepo;

@Service
public class ErrorService {
	
	private ErrorRepo repo;
	
	@Autowired
	public ErrorService(ErrorRepo repo) {
		this.repo = repo;
	}
	
	public GluaError create(GluaError err) {
		return this.repo.save(err);
	}
}
