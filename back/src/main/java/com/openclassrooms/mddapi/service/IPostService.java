package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostForListDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

import java.util.List;

public interface IPostService {

    void savePost(User user, Topic topic, PostCreateDTO postCreateDTO);

    List<PostForListDTO> getPosts(User user);

    PostDTO getPostById(Long postId);
}
