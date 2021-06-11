package com.qa.errortracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.GluaError;
import com.qa.errortracker.domain.GluaErrorDTO;
import com.qa.errortracker.repo.ErrorRepo;
import com.qa.errortracker.utils.GluaErrorMapper;

@Service
public class ErrorService {
	
	private ErrorRepo repo;
	private GluaErrorMapper mapper;
	
	@Autowired
	public ErrorService(ErrorRepo repo, GluaErrorMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	public GluaErrorDTO create(GluaError err) {
		return this.mapper.toDTO(this.repo.save(err));
	}
}
