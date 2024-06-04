package com.example.demo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Otp extends BaseObject{
    private String email;

    private String otpKey;

    private Date date;

    public Otp(String email, String key, Date date) {
        this.email = email;
        this.otpKey = key;
        this.date = date;
    }
}