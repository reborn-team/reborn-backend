package com.reborn.reborn.comment.domain.repository;

import com.reborn.reborn.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
