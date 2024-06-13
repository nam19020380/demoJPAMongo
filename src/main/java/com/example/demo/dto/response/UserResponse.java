package com.example.demo.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String userName;

    private Date userBirthday;

    private String userAvatarLink;

    private String userJob;
}
