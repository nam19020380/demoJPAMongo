package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseObject implements Serializable{
    @Id
    private Integer id;

    private String email;

    private String password;

    private String userName;

    private Date userBirthday;

    private String userAvatarLink;

    private String userJob;

    public User(String email, String password, String userName, Date userBirthday, String userAvatarLink, String userJob) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userBirthday = userBirthday;
        this.userAvatarLink = userAvatarLink;
        this.userJob = userJob;
    }

    public User(String email, String password, String userName, Date userBirthday) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userBirthday = userBirthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", userBirthday=" + userBirthday +
                ", userAvatarlink='" + userAvatarLink + '\'' +
                ", userJob='" + userJob + '\'' +
                '}';
    }
}