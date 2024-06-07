package com.example.demo.respository;

import com.example.demo.entity.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface FriendshipRepository extends MongoRepository<Friendship, String> {
    public Integer countByUserIdAndSideAndDateGreaterThanEqualAndDelFlagIsFalse(String userId,String side, Date date);

    public List<Friendship> findByUserIdAndDelFlagIsFalse(String userId);

    public List<Friendship> findByUserIdAndSideAndDelFlagIsFalse(String userId, String side);

    public Friendship findByUserIdAndFriendIdAndDelFlagIsFalse(String userId, String side);

    public List<Friendship> findByFriendIdAndSideAndDelFlagIsFalse(String friendId, String side);

    public Friendship findByIdAndDelFlagIsFalse(String friendshipId);

    public Boolean existsByUserIdAndFriendIdAndSideAndDelFlagIsFalse(String userId, String friendId, String side);

    public Boolean existsByUserIdAndFriendIdAndDelFlagIsFalse(String userId, String friendId);

    public Boolean existsByUserIdAndFriendIdAndSide(String userId, String friendId, String side);

    public Boolean existsByUserIdAndFriendId(String userId, String friendId);

    public Integer countByUserIdAndSideAndDelFlagIsFalseAndDateGreaterThanEqual(String userId, String side, Date date);
}
