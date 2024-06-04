package com.example.demo.entity;


import lombok.*;
import java.sql.Date;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Comment extends BaseObject{
    private String userId;

    private String commentId;

    private String postId;

    private String content;

    private String imageLink;

    private Date date;
}