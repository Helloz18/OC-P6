package com.openclassrooms.mddapi.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDetailDTO {

    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String topicName;
    private List<CommentDTO>
            comments;

}
