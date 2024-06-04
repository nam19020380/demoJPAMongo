package com.example.demo.respository;

import com.example.demo.entity.Emote;
import com.example.demo.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface EmoteRepository extends MongoRepository<Post, String> {
    public Integer countByUserIdAndDelFlagIsFalseAndDateGreaterThanEqual(String userId, Date date);

    public List<Emote> findByCommentIdAndDelFlagIsFalse(String commentId);

    public List<Emote> findByPostIdAndDelFlagIsFalse(String statusId);

    public Integer countByPostIdAndDelFlagIsFalse(String statusId);

    public Integer countByCommentIdAndDelFlagIsFalse(String commentId);

    public Boolean existsByUserIdAndIdAndDelFlagIsFalse(String userId, String emoteId);

    public Boolean existsByUserIdAndPostIdAndDelFlagIsFalse(String userId, String statusId);

    public Boolean existsByUserIdAndCommentIdAndDelFlagIsFalse(String userId, String commentId);
}
