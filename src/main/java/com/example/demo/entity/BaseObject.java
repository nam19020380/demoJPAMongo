package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BaseObject {
    @Id
    private Integer Id;

    private boolean delFlag;

    private Date createDate;

    private String createUserName;

    private Date updateDate;

    private String updateUserName;
}
