package com.qa.errortracker.utils;

import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.Developer;
import com.qa.errortracker.domain.DeveloperDTO;

@Service
public class DeveloperMapper implements Mapper<DeveloperDTO, Developer> {

	@Override
	public DeveloperDTO toDTO(Developer dev) {
		DeveloperDTO dto = new DeveloperDTO();
		
		dto.setId(dev.getId());
		
		return dto;
	}
	
	@Override
	public Developer fromDTO(DeveloperDTO dto) {
		Developer dev = new Developer();
		dev.setId(dto.getId());
		
		return dev;
	}
	
}