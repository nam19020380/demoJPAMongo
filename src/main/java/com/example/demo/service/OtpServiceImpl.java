package com.example.demo.service;

import com.example.demo.entity.Otp;
import com.example.demo.respository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService{
    @Autowired
    OtpRepository otpRepository;

    public Otp createOtp(String email){
        Long key = Math.round(Math.random() * (999999 - 100000 + 1) + 100000);
        String keyString = key.toString();
        return new Otp(email, keyString, new Date(new Date().getTime()));
    }

    public void saveOtp(Otp otp){
        Optional<Otp> optionalOtp = otpRepository.findByEmailAndDelFlagIsFalse(otp.getEmail());
        if(optionalOtp.isPresent()){
            optionalOtp.get().setOtpKey(otp.getOtpKey());
            optionalOtp.get().setDate(otp.getDate());
            otpRepository.save(optionalOtp.get());
        } else {
            otpRepository.save(otp);
        }
    }

    @Override
    public Optional<Otp> findByEmail(String email) {
        return otpRepository.findByEmailAndDelFlagIsFalse(email);
    }
}