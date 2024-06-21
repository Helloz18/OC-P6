package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateDTO {

    private String title;
    private String content;
    private Long topicId;
}
