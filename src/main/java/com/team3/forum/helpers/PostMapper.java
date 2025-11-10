package com.team3.forum.helpers;

import com.team3.forum.models.Post;
import com.team3.forum.models.dtos.PostCreationDto;
import com.team3.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final UserRepository userRepository;

    @Autowired
    public PostMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Post toEntity(PostCreationDto dto){
        Post post = new Post();
        post.setUser(userRepository.findById(dto.getUserId()));
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return post;
    }

    public Post toEntity(PostCreationDto dto, int id){
        Post post = this.toEntity(dto);
        post.setId(id);
        return post;
    }
}
