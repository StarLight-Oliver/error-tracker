package com.qa.errortracker.utils;

import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.Developer;
import com.qa.errortracker.domain.DeveloperDTO;

@Service
public class DeveloperMapper implements Mapper<DeveloperDTO, Developer> {

	@Override
	public DeveloperDTO toDTO(Developer dev) {
		var dto = new DeveloperDTO();
		
		dto.setId(dev.getId());
		dto.setName(dev.getName());
				
		return dto;
	}
	
	@Override
	public Developer fromDTO(DeveloperDTO dto) {
		var dev = new Developer();
		dev.setId(dto.getId());
		dev.setName(dto.getName());
		
		return dev;
	}
	
}
