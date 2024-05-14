package com.example.demo.respository;

import com.example.demo.entity.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface FriendshipRepository extends MongoRepository<Friendship, String> {
    public Integer countByUserIdAndSideAndDateGreaterThanEqualAndDelFlagIsFalse(Integer userId,Integer side, Date date);

    public List<Friendship> findByUserIdAndDelFlagIsFalse(Integer userId);

    public List<Friendship> findByUserIdAndSideAndDelFlagIsFalse(Integer userId, Integer side);

    public List<Friendship> findByFriendIdAndSideAndDelFlagIsFalse(Integer friendId, Integer side);

    public Friendship findByFriendshipIdAndDelFlagIsFalse(Integer friendshipId);

    public Boolean existsByUserIdAndFriendIdAndSideAndDelFlagIsFalse(Integer userId, Integer friendId, Integer side);

    public Boolean existsByUserIdAndFriendIdAndDelFlagIsFalse(Integer userId, Integer friendId);

    public Boolean existsByUserIdAndFriendIdAndSide(Integer userId, Integer friendId, Integer side);

    public Boolean existsByUserIdAndFriendId(Integer userId, Integer friendId);

    public Integer countByUserIdAndSideAndDelFlagIsFalseAndDateGreaterThanEqual(Integer userId, Integer side, Date date);
}
