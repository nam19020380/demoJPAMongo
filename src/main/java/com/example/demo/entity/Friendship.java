package com.example.demo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship extends BaseObject{
    private String userId;

    private String friendId;

    private String side;

    private Date date;
}