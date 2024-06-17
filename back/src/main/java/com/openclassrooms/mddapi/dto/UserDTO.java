package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private String name;
    @JsonIgnore
    private String password;
    private String email;
//    @JsonIgnore
//    private List<Post> posts;
//    @JsonIgnore
//    private List<Comment> comments;
//
    private Set<Topic> topics;

}
