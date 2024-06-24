package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.Post;
import com.lyfter.backend.model.PostComment;
import com.lyfter.backend.model.User;
import com.lyfter.backend.payload.request.PostRequest;
import com.lyfter.backend.repo.PostCommentRepository;
import com.lyfter.backend.repo.PostRepository;
import com.lyfter.backend.repo.UserRepository;
import com.lyfter.backend.service.PostCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCRUDServiceImpl implements PostCRUDService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCommentRepository postCommentRepository;

    @Autowired
    public PostCRUDServiceImpl(PostRepository postRepository, UserRepository userRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public List<Post> getAllPosts() throws Exception {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) throw new Exception("There are no posts");

        return posts;
    }

    @Override
    public Post getPostById(int postId) throws Exception {
        if (postId < 0) throw new Exception("Post id must be greater than 0");

        return postRepository.findById(postId)
                .orElseThrow(() -> new Exception("Post not found"));
    }

    @Override
    public void createPost(PostRequest postRequest) throws Exception {
        if (postRequest == null) throw new Exception("Post must be provided.");

        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new Exception("User not found."));

        Post post = new Post(postRequest.getBody(), user);

        postRepository.save(post);
    }

    @Override
    public void deletePost(int postId) throws Exception {
        if (postId < 0) throw new Exception("PostID provided must be greater than 0");
        if (!postRepository.existsById(postId)) throw new Exception("Post with ID: " + postId + " not found.");

        List<PostComment> postComments = postCommentRepository.findByPostId(postId);

        postCommentRepository.deleteAll(postComments);

        postRepository.deleteById(postId);
    }
}
