package com.scy.driving.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scy.driving.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
}
