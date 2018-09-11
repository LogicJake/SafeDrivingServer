package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;

import com.scy.driving.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{
	
}
