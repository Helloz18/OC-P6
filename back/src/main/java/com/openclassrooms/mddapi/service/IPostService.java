package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

public interface IPostService {

    void savePost(User user, Topic topic, PostCreateDTO postCreateDTO);


}
