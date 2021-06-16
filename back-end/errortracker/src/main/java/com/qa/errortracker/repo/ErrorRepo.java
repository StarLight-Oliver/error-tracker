package com.qa.errortracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.errortracker.domain.GluaError;

@Repository
public interface ErrorRepo extends JpaRepository<GluaError, String> {

}
