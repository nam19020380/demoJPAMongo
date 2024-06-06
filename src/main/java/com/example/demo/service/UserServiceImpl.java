package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public String saveUser(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndDelFlagIsFalse(email);
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(String.valueOf(id)).get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean checkIfExistByEmail(String email) {
        return userRepository.existsByEmailAndDelFlagIsFalse(email);
    }
}
