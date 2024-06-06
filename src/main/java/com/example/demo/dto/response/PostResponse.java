package com.example.demo.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private String id;
    private String text;
    private String ownerName;
    private Date date;
    private Integer emoteCount;
    private Integer commentCount;
}
