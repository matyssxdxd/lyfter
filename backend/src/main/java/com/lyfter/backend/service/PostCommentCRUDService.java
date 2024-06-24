package com.lyfter.backend.service;

import com.lyfter.backend.model.PostComment;
import com.lyfter.backend.payload.request.PostCommentRequest;

import java.util.List;

public interface PostCommentCRUDService {

    List<PostComment> getByPostId(int postId) throws Exception;

    void createPostComment(PostCommentRequest postCommentRequest) throws Exception;

    void deletePostComment(int postCommentId) throws Exception;
}
