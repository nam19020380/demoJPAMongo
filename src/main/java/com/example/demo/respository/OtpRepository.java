package com.example.demo.respository;

import com.example.demo.entity.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OtpRepository extends MongoRepository<Otp, String> {
    public Optional<Otp> findByEmailAndDelFlagIsFalse(String email);
    public Otp findTopByOrderByIdDesc();
}
