package com.team3.forum.services;

import com.team3.forum.models.Folder;
import com.team3.forum.models.Post;
import com.team3.forum.models.User;
import com.team3.forum.models.folderDtos.FolderUpdateDto;
import com.team3.forum.models.postDtos.PostUpdateDto;

import java.util.List;

public interface FolderService {
    List<Folder> findAll();

    Folder findById(int id);

    void deleteById(int id, User requester);

    Folder create(Folder folder, User requester);

    Folder update(int folderId, FolderUpdateDto folderUpdateDto, User requester);

    Folder getFolderBySlug(String slug);

    List<Post> getPostsInFolder(Folder folder);

    List<Folder> findHomeFolders();

    Folder getFolderByPath(List<String> slugs);
}
