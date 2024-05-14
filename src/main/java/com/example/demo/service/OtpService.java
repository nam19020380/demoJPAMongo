package com.example.demo.service;

import com.example.demo.entity.Otp;

import java.util.Optional;

public interface OtpService {
    public Otp createOtp(String email);
    public void saveOtp(Otp otp);
    public Optional<Otp> findByEmail(String email);
}
