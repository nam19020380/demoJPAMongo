package com.example.demo.entity;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BaseObject {
    private boolean delFlag;

    private Date createDate;

    private String createUserName;

    private Date updateDate;

    private String updateUserName;
}
