package com.qa.errortracker.utils;

import org.springframework.stereotype.Service;

import com.qa.errortracker.domain.GluaError;
import com.qa.errortracker.domain.GluaErrorDTO;

@Service
public class GluaErrorMapper implements Mapper<GluaErrorDTO, GluaError> {

	@Override
	public GluaErrorDTO toDTO(GluaError err) {
		GluaErrorDTO dto = new GluaErrorDTO();
		
		dto.setHash(err.getHash());
		
		return dto;
	}
	
	@Override
	public GluaError fromDTO(GluaErrorDTO dto) {
		GluaError err = new GluaError();
		err.setHash(dto.getHash());
		
		return err;
	}
	
}
