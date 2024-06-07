package com.example.demo.service;



import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    public String saveUser(User user);

    public User findUserByEmail(String email);

    public User findUserById(String id);

    public List<User> findAll();

    public boolean checkIfExistByEmail(String email);
}