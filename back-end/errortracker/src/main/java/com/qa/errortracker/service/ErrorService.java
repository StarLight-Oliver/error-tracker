package com.qa.errortracker.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

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
	
	public GluaErrorDTO create(GluaErrorDTO errDTO) {
		GluaError err = this.mapper.fromDTO(errDTO);
		
		return this.mapper.toDTO(this.repo.save(err));
	}
	
	public List<GluaErrorDTO> getAll() {
		return this.repo.findAll().stream().map(err -> this.mapper.toDTO(err)).collect(Collectors.toList());
	}
	
	public GluaErrorDTO get(String hash) {
		GluaError existing = this.repo.findById(hash).orElseThrow(() -> new EntityNotFoundException());
		
		return this.mapper.toDTO(existing);
	}
	
	public GluaErrorDTO update(GluaErrorDTO errDTO, String hash) {
		GluaError err = this.mapper.fromDTO(errDTO);
		
		GluaError existing = this.repo.findById(hash).orElseThrow(() -> new EntityNotFoundException());
		
		existing.setCount(err.getCount());
		existing.setDevelopers(err.getDevelopers());
		existing.setName(err.getName());
		existing.setState(err.getState());
				
		GluaError updated = this.repo.save(existing);
		
		return this.mapper.toDTO(updated);
	}
	
	public boolean delete(String hash) {
		this.repo.deleteById(hash);
		return !this.repo.existsById(hash);
	}
}
