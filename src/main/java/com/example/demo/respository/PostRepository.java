package com.example.demo.respository;

import com.example.demo.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    public Post findByIdAndDelFlagIsFalse(String id);

    public List<Post> findByUserIdAndDateAndDelFlagIsFalseOrderById(String userId, Date date);

    public Integer countByUserIdAndDelFlagIsFalseAndDateGreaterThanEqual(String userId, Date date);

    public List<Post> findByUserIdAndDelFlagIsFalseOrderByDate(String userId);

    public List<Post> findByUserIdAndDelFlagIsFalse(String userId);

    public Boolean existsByUserIdAndIdAndDelFlagIsFalse(String userId, String statusId);

    public List<Post> findByUserIdInAndDelFlagIsFalseOrderByDate(List<Integer> friendList, Pageable pageable);
}
