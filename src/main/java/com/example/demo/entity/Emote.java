package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emote extends BaseObject{
    @Id
    private Integer Id;

    private Integer userId;

    private Integer commentId;

    private Integer statusId;

    private Date date;
}
