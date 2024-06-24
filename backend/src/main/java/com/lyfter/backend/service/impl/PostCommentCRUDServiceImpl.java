package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.Post;
import com.lyfter.backend.model.PostComment;
import com.lyfter.backend.model.User;
import com.lyfter.backend.payload.request.PostCommentRequest;
import com.lyfter.backend.repo.PostCommentRepository;
import com.lyfter.backend.repo.PostRepository;
import com.lyfter.backend.repo.UserRepository;
import com.lyfter.backend.service.PostCommentCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentCRUDServiceImpl implements PostCommentCRUDService {

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public List<PostComment> getByPostId(int postId) throws Exception {
        if (postId < 0) throw new Exception("Post id must be greater than 0");

        List<PostComment> comments = postCommentRepository.findByPostId(postId);

        if (comments.isEmpty()) throw new Exception("No comments found for post id " + postId);

        return comments;
    }

    @Override
    public void createPostComment(PostCommentRequest postCommentRequest) throws Exception {
        if (postCommentRequest == null) throw new Exception("Post comment must be provided.");

        User user = userRepository.findById(postCommentRequest.getUserId())
                .orElseThrow(() -> new Exception("User not found."));

        Post post = postRepository.findById(postCommentRequest.getPostId())
                .orElseThrow(() -> new Exception("Post not found."));

        PostComment postComment = new PostComment();

        postComment.setUser(user);
        postComment.setBody(postCommentRequest.getBody());
        postComment.setPost(post);

        postCommentRepository.save(postComment);
    }

    @Override
    public void deletePostComment(int postCommentId) throws Exception {
        if (postCommentId < 0) throw new Exception("Post comment ID must be greater than 0");
        if (postCommentRepository.existsById(postCommentId)) throw new Exception("Post comment with id " + postCommentId + " doesn't exist.");

        postCommentRepository.deleteById(postCommentId);
    }
}
