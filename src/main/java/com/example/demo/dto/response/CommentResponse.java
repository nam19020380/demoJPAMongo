package com.example.demo.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private String ownerName;
    private String text;
    private Date date;
    private Integer emoteCount;
    private Integer commentCount;
}
