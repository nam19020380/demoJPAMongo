package com.example.demo.service;

import com.example.demo.dto.request.FriendRequest;
import com.example.demo.dto.response.FriendshipResponse;
import com.example.demo.entity.Friendship;
import com.example.demo.respository.FriendshipRepository;
import com.example.demo.sercurity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService{
    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    UserService userService;

    public void saveFriendship(Friendship friendship){
        friendshipRepository.save(friendship);
    }

    public ResponseEntity<?> sendRequest(FriendRequest friendRequest){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Friendship friendship = new Friendship();
            if(!friendshipRepository.existsByUserIdAndFriendIdAndDelFlagIsFalse(userDetails.getId(), userService.findUserByEmail(friendRequest.getUserEmail()).getId())){
                friendship.setFriendId(userService.findUserByEmail(friendRequest.getUserEmail()).getId());
                friendship.setUserId(userDetails.getId());
                friendship.setSide("1");
                friendship.setDate(new java.sql.Date(new java.util.Date().getTime()));
                friendship.setCreateUserName(userDetails.getUsername());
                friendship.setCreateDate(new Date());
                friendshipRepository.save(friendship);
                return new ResponseEntity<>("Tao request thanh cong", HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest()
                        .body("Already sent invitation");
            }

        } catch(Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> acceptRequest(String id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Friendship friendship = friendshipRepository.findByIdAndDelFlagIsFalse(id);
            if(friendshipRepository.existsByUserIdAndFriendIdAndSideAndDelFlagIsFalse(friendship.getUserId(), userDetails.getId(), "1")){
                java.util.Date date = new java.util.Date(new java.util.Date().getTime());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                friendship.setSide("2");
                friendship.setDate(sqlDate);
                friendship.setUpdateUserName(userDetails.getUsername());
                friendship.setUpdateDate(new Date());
                Friendship friendship1 = new Friendship(friendship.getFriendId()
                        , friendship.getUserId(), "2", sqlDate);
                friendship1.setUpdateUserName(userDetails.getUsername());
                friendship1.setUpdateDate(new Date());
                friendshipRepository.save(friendship);
                friendshipRepository.save(friendship1);
                return new ResponseEntity<>("Accept thanh cong", HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest()
                        .body("Invalid invitation");
            }
        } catch(Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteFriendship(String id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Friendship friendship = friendshipRepository.findByIdAndDelFlagIsFalse(id);
            if(friendshipRepository.existsByUserIdAndFriendIdAndDelFlagIsFalse(friendship.getUserId(), userDetails.getId())){
                friendship.setDelFlag(true);
                friendship.setUpdateUserName(userDetails.getUsername());
                friendship.setUpdateDate(new Date());
                friendshipRepository.save(friendship);
                if(friendship.getSide().equals("2")){
                    Friendship friendship1 = friendshipRepository.findByUserIdAndFriendIdAndDelFlagIsFalse(friendship.getFriendId(), friendship.getUserId());
                    friendship1.setDelFlag(true);
                    friendship1.setUpdateUserName(userDetails.getUsername());
                    friendship1.setUpdateDate(new Date());
                    friendshipRepository.save(friendship1);
                }
                return new ResponseEntity<>("Xoa ban be thanh cong", HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't delete other's friendship");
            }
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Friendship> findByUserId(String userId){
        return friendshipRepository.findByUserIdAndDelFlagIsFalse(userId);
    }

    public ResponseEntity<?> getAllUserRequests() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<Friendship> friendshipList = friendshipRepository.findByFriendIdAndSideAndDelFlagIsFalse(userDetails.getId(), "1");
            List<FriendshipResponse> responseList = new ArrayList<>();
            for (Friendship friendship : friendshipList) {
                FriendshipResponse friendshipResponse = new FriendshipResponse();
                friendshipResponse.setId(friendship.getId());
                friendshipResponse.setFromId(friendship.getUserId());
                friendshipResponse.setDate(friendship.getDate());
                responseList.add(friendshipResponse);
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllUserFriends(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<Friendship> friendshipList = friendshipRepository.findByFriendIdAndSideAndDelFlagIsFalse(userDetails.getId(), "2");
            List<FriendshipResponse> responseList = new ArrayList<>();
            for(Friendship friendship : friendshipList){
                FriendshipResponse friendshipResponse = new FriendshipResponse();
                friendshipResponse.setId(friendship.getId());
                friendshipResponse.setFromId(friendship.getUserId());
                friendshipResponse.setDate(friendship.getDate());
                responseList.add(friendshipResponse);
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Friendship> findByUserIdAndSide(String userId, String side){
        return friendshipRepository.findByUserIdAndSideAndDelFlagIsFalse(userId, side);
    }

    public List<Friendship> findByFriendIdAndSide(String friendId, String side){
        return friendshipRepository.findByFriendIdAndSideAndDelFlagIsFalse(friendId, side);
    }

    public Friendship findByFriendshipId(String friendshipId){
        return friendshipRepository.findByIdAndDelFlagIsFalse(friendshipId);
    }

    public Integer reportCount(String userId, Date date){
        return friendshipRepository.countByUserIdAndSideAndDelFlagIsFalseAndDateGreaterThanEqual(userId, "2", date);
    }

    public Boolean existsByUserIdAndFriendId(String userId, String friendId){
        return friendshipRepository.existsByUserIdAndFriendIdAndDelFlagIsFalse(userId, friendId);
    }

    public Boolean existsByUserIdAndFriendIdAndSide(String userId, String friendId, String side){
        return friendshipRepository.existsByUserIdAndFriendIdAndSideAndDelFlagIsFalse(userId, friendId, side);
    }
}
