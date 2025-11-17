package com.team3.forum.services;

import com.team3.forum.models.Tag;
import com.team3.forum.models.User;
import java.util.List;

public interface TagService {
    Tag createTag(Tag tag, User requester);
    Tag updateTag(int id, Tag tag, User requester);
    Tag findById(int id);
    List<Tag> findAll();
    void deleteById(int id, User requester);
}