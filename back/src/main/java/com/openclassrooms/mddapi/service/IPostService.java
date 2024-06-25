package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

import java.util.List;

public interface IPostService {

    void savePost(User user, Topic topic, PostCreateDTO postCreateDTO);

    List<PostDTO> getPosts(User user);

    PostDetailDTO getPostById(Long postId);

    Post findPostById(Long postId);
}
