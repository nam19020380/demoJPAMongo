package com.example.demo.dto.request;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {
    private String id;

    private String postId;

    private String commentId;

    private String text;

    private MultipartFile file;
}
