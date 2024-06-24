package com.lyfter.backend.repo;

import com.lyfter.backend.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    List<PostComment> findByPostId(int postId);
}
