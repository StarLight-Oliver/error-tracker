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
		dto.setName(err.getName());
		dto.setCount(err.getCount());
		dto.setShortErr(err.getShortErr());
		dto.setStack(err.getStack());
		dto.setState(err.getState());
		
		return dto;
	}
	
	@Override
	public GluaError fromDTO(GluaErrorDTO dto) {
		GluaError err = new GluaError();
		err.setHash(dto.getHash());
		err.setName(dto.getName());
		err.setCount(dto.getCount());
		err.setShortErr(dto.getShortErr());
		err.setStack(dto.getStack());
		err.setState(dto.getState());
		
		
		return err;
	}
	
}
