package com.example.demo.respository;

import com.example.demo.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    public Comment findByIdAndDelFlagIsFalse(String commentId);

    public List<Comment> findByUserIdAndDelFlagIsFalse(String userId);

    public Integer countByUserIdAndDelFlagIsFalseAndDateGreaterThanEqual(String userId, Date date);

    public List<Comment> findByCommentIdAndDelFlagIsFalse(String commentId);

    public List<Comment> findByPostIdAndDelFlagIsFalse(String statusId);

    public Integer countByPostIdAndDelFlagIsFalse(String statusId);

    public Integer countById(String commentId);

    public Integer countByCommentIdAndDelFlagIsFalse(String commentId);

    public Boolean existsByUserIdAndIdAndDelFlagIsFalse(String userId, String commentId);
}
