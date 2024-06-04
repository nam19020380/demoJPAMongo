package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Post extends BaseObject{
    private String userId;

    private String content;

    private String imageLink;

    private Date date;
}
