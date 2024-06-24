package com.lyfter.backend.service;

import com.lyfter.backend.model.Post;
import com.lyfter.backend.payload.request.PostRequest;

import java.util.List;

public interface PostCRUDService {

    List<Post> getAllPosts() throws Exception;

    Post getPostById(int postId) throws Exception;

    void createPost(PostRequest postRequest) throws Exception;

    void deletePost(int postId) throws Exception;
}
