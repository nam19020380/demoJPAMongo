package com.example.demo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    @Id
    private Integer friendshipId;

    private Integer userId;

    private Integer friendId;

    private int side;

    private Date date;
}