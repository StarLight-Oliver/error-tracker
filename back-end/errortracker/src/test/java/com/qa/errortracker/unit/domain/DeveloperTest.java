package com.qa.errortracker.unit.domain;

import org.junit.jupiter.api.Test;

import com.qa.errortracker.domain.Developer;
import com.qa.errortracker.domain.DeveloperDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

class DeveloperTest {
//	@Test
//	public void testDAO() {
//		EqualsVerifier.simple().forClass(Developer.class).verify();
//	}

	@Test
	void testDTO() {
		EqualsVerifier.simple().forClass(DeveloperDTO.class).verify();
	}
}
