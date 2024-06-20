package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

public interface IPostService {

    void savePost(User user, Topic topic, PostDTO postDTO);
}
