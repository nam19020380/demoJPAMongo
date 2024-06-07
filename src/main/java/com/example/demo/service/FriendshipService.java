package com.example.demo.service;

import com.example.demo.dto.request.FriendRequest;
import com.example.demo.entity.Friendship;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface FriendshipService {
    public void saveFriendship(Friendship friendship);

    public ResponseEntity<?> sendRequest(FriendRequest friendRequest);

    public ResponseEntity<?> acceptRequest(String id);

    public ResponseEntity<?> deleteFriendship(String id);

    public ResponseEntity<?> getAllUserRequests();

    public ResponseEntity<?> getAllUserFriends();

    public List<Friendship> findByUserId(String userId);

    public List<Friendship> findByUserIdAndSide(String userId, String side);

    public List<Friendship> findByFriendIdAndSide(String friendId, String side);

    public Friendship findByFriendshipId(String friendshipId);

    public Integer reportCount(String userId, Date date);

    public Boolean existsByUserIdAndFriendId(String userId, String friendId);

    public Boolean existsByUserIdAndFriendIdAndSide(String userId, String friendId, String side);
}
