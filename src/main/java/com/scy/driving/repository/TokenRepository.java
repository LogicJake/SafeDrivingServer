package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
	boolean existsByUidAndToken(Long uid, String token);
	
	Token findByUid(Long uid);
}
