package com.example.demo.service;



import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    public Integer saveUser(User user);

    public User findUserByEmail(String email);

    public User findUserById(Integer id);

    public List<User> findAll();

    public boolean checkIfExistByEmail(String email);
}