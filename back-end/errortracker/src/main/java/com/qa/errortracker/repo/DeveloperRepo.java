package com.qa.errortracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.errortracker.domain.Developer;

@Repository
public interface DeveloperRepo extends JpaRepository<Developer, Long> {

}
