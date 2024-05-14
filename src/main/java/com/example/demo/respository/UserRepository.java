package com.example.demo.respository;

import com.example.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByEmailAndDelFlagIsFalse(String email);
    public List<User> findByUserNameAndDelFlagIsFalse(String userName);
    public boolean existsByEmailAndDelFlagIsFalse(String email);
    public User findTopByOrderByIdDesc();
}
