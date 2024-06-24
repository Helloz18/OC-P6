package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;

public interface ICommentService {

    void saveComment(User user, Post post, String content);
}
