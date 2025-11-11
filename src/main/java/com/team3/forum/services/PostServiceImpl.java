package com.team3.forum.services;

import com.team3.forum.exceptions.DuplicateEntityException;
import com.team3.forum.exceptions.EntityNotFoundException;
import com.team3.forum.models.Post;
import com.team3.forum.models.User;
import com.team3.forum.repositories.PostRepository;
import com.team3.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService{
    public static final String ALREADY_LIKED_ERROR = "This post is already liked by this user.";
    public static final String NOT_LIKED_ERROR = "This post is not liked by this user.";
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Post findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public int getLikes(int postId) {
        Post post = postRepository.findById(postId);
        return post.getLikedBy().size();
    }

    @Override
    public void likePost(int postId, int userId) {
        Post post = postRepository.findById(postId);
        User user = userRepository.findById(userId);

        if (post.getLikedBy().contains(user)){
            throw new DuplicateEntityException(ALREADY_LIKED_ERROR);
        }

        post.getLikedBy().add(user);
        user.getLikedPosts().add(post);

        postRepository.save(post);
    }

    @Override
    public void unlikePost(int postId, int userId) {
        Post post = postRepository.findById(postId);
        User user = userRepository.findById(userId);

        if (!post.getLikedBy().contains(user)){
            throw new EntityNotFoundException(NOT_LIKED_ERROR);
        }

        post.getLikedBy().remove(user);
        user.getLikedPosts().remove(post);

        postRepository.save(post);
    }
}
