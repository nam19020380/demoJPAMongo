package com.example.demo.respository;

import com.example.demo.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    public Comment findByIdAndDelFlagIsFalse(Integer commentId);

    public List<Comment> findByUserIdndDelFlagIsFalse(Integer userId);

    public Integer countByUserIdndDelFlagIsFalseAndDateGreaterThanEqual(Integer userId, Date date);

    public List<Comment> findByCommentIdndDelFlagIsFalse(Integer commentId);

    public List<Comment> findByPostIdndDelFlagIsFalse(Integer statusId);

    public Integer countByPostId(Integer statusId);

    public Integer countById(Integer commentId);

    public Integer countByCommentId(Integer commentId);

    public Boolean existsByUserIdAndCommentId(Integer userId, Integer commentId);
}
