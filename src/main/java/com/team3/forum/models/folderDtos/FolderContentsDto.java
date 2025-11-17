package com.team3.forum.models.folderDtos;

import com.team3.forum.models.postDtos.PostResponseDto;

import java.util.List;

public record FolderContentsDto(
        FolderResponseDto folder,
        List<FolderResponseDto> subFolders,
        List<PostResponseDto> posts
) {}
