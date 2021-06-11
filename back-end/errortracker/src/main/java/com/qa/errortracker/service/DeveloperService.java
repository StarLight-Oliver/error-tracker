package com.qa.errortracker.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.Developer;
import com.qa.errortracker.domain.DeveloperDTO;
import com.qa.errortracker.repo.DeveloperRepo;
import com.qa.errortracker.utils.DeveloperMapper;

@Service
public class DeveloperService {

	private DeveloperRepo repo;
	private DeveloperMapper mapper;
	
	@Autowired
	public DeveloperService(DeveloperRepo repo, DeveloperMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	public DeveloperDTO create(Developer dev) {
		return this.mapper.toDTO(this.repo.save(dev));
	}
	
	public List<DeveloperDTO> getAll() {
		return this.repo.findAll().stream().map(dev -> this.mapper.toDTO(dev)).collect(Collectors.toList());
	}
	
	public DeveloperDTO get(Long id) {
		Developer existing = this.repo.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return this.mapper.toDTO(existing);
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
