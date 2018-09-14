package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;

import com.scy.driving.entity.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
	boolean existsByUidAndToken(Long uid, String token);
	
	Token findByUid(Long uid);
}
