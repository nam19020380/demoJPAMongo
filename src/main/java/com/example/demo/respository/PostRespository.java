package com.example.demo.respository;

import com.example.demo.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface PostRespository extends MongoRepository<Post, String> {
    public Post findByIdAndDelFlagIsFalse(Integer id);

    public List<Post> findByUserIdAndDateAndDelFlagIsFalseOrderById(Integer userId, Date date);

    public Integer countByUserIdAndDelFlagIsFalseAndDateGreaterThanEqual(Integer userId, Date date);

    public List<Post> findByUserIdAndDelFlagIsFalseOrderByDate(Integer userId);

    public List<Post> findByUserIdAndDelFlagIsFalse(Integer userId);

    public Boolean existsByUserIdAndIdAndDelFlagIsFalse(Integer userId, Integer statusId);

    public List<Post> findByUserIdInAndDelFlagIsFalse(List<Integer> friendList);
}
