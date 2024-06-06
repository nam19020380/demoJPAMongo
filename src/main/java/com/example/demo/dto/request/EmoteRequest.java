package com.example.demo.dto.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class EmoteRequest {
    private String id;

    private String postId;

    private String commentId;
}
