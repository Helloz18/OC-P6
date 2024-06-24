package com.openclassrooms.mddapi.dto;

import lombok.Getter;

@Getter
public class PostCreateDTO {

    private String title;
    private String content;
    private Long topicId;
}
