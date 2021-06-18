package com.qa.errortracker.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.Developer;
import com.qa.errortracker.domain.DeveloperDTO;
import com.qa.errortracker.domain.GluaError;
import com.qa.errortracker.domain.GluaErrorDTO;

@Service
public class GluaErrorMapper implements Mapper<GluaErrorDTO, GluaError> {

	private DeveloperMapper mapper;
	
	@Autowired
	
	public GluaErrorMapper(DeveloperMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public GluaErrorDTO toDTO(GluaError err) {
		var dto = new GluaErrorDTO();
		
		dto.setHash(err.getHash());
		dto.setName(err.getName());
		dto.setCount(err.getCount());
		dto.setShortErr(err.getShortErr());
		dto.setStack(err.getStack());
		dto.setState(err.getState());
		
		List<DeveloperDTO> devs = new ArrayList<>();
		for (Developer dev : err.getDevelopers()) {
			devs.add(this.mapper.toDTO(dev));
		}
		dto.setDevelopers(devs);
		
		return dto;
	}
	
	@Override
	public GluaError fromDTO(GluaErrorDTO dto) {
		var err = new GluaError();
		err.setHash(dto.getHash());
		err.setName(dto.getName());
		err.setCount(dto.getCount());
		err.setShortErr(dto.getShortErr());
		err.setStack(dto.getStack());
		err.setState(dto.getState());
		
		List<Developer> devs = new ArrayList<>();
		for (DeveloperDTO devDTO : dto.getDevelopers()) {
			devs.add(this.mapper.fromDTO(devDTO));
		}
		err.setDevelopers(devs);
		
		return err;
	}
	
}
