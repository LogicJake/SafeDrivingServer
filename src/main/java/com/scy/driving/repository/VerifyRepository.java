package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.Verify;

@Repository
public interface VerifyRepository extends CrudRepository<Verify, Long> {
	
}
