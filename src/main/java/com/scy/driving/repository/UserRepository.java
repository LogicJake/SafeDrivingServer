package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserNameAndPassword(String userName, String password);
	
	User findByUserName(String userName);
	
	User findByUid(Long uid);
	
	boolean existsByUserName(String userName);
}
