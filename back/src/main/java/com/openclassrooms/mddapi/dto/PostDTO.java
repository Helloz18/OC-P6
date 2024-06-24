package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt;
}
