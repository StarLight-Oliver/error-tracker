package com.qa.errortracker.utils;

public interface Mapper<DTO, DAO> {
	DTO toDTO(DAO dao);
	DAO fromDTO(DTO dto);
}