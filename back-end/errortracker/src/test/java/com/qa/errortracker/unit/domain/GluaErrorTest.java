package com.qa.errortracker.unit.domain;
import org.junit.jupiter.api.Test;

import com.qa.errortracker.domain.GluaErrorDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

class GluaErrorTest {
//	@Test
//	public void testDAO() {
//		EqualsVerifier.simple().forClass(GluaError.class).verify();
//	}

	@Test
	void testDTO() {
		EqualsVerifier.simple().forClass(GluaErrorDTO.class).verify();
	}
}
