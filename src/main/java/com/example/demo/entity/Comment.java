package com.example.demo.entity;


import lombok.*;
import java.sql.Date;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Comment {
    @Id
    private Integer Id;

    private Integer userId;

    private Integer commentId;

    private Integer statusId;

    private String content;

    private String imageLink;

    private Date date;
}