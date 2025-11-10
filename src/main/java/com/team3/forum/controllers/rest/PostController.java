package com.team3.forum.controllers.rest;

import com.team3.forum.helpers.PostMapper;
import com.team3.forum.models.Post;
import com.team3.forum.models.dtos.PostCreationDto;
import com.team3.forum.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public List<Post> getAll() {
        return postService.findAll();
    }

    @PostMapping
    public Post create(@RequestBody @Valid PostCreationDto postCreationDto) {
        Post post = postMapper.toEntity(postCreationDto);
        return postService.create(post);
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable int postId) {
        return postService.findById(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@RequestBody @Valid PostCreationDto postCreationDto, @PathVariable int postId){
        Post post = postMapper.toEntity(postCreationDto, postId);
        return postService.update(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable int postId) {
        postService.deleteById(postId);
    }
}
