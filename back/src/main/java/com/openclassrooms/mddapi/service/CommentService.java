package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentService implements ICommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Save a comment. A comment has a content, is written by a User about a Post.
     * @param user
     * @param post
     * @param content
     */
    @Override
    public void saveComment(User user, Post post, String content) {
        Comment
                comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(String.valueOf(Instant.now()));

        commentRepository.save(comment);
    }
}
