package com.example.demo.controller;

import com.example.demo.dto.request.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.FriendshipService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/friendship")
public class FriendController {
    @Autowired
    FriendshipService friendshipService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> sendRequest(@RequestBody FriendRequest friendRequest){
        return friendshipService.sendRequest(friendRequest);
    }

    @PutMapping
    public ResponseEntity<?> acceptRequest(@RequestParam String id){
        return friendshipService.acceptRequest(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFriendship(@RequestParam String id){
        return friendshipService.deleteFriendship(id);
    }

    @GetMapping(value = "/requests/all")
    public ResponseEntity<?> getAllUserRequests(){
        return friendshipService.getAllUserRequests();
    }

    @GetMapping(value = "/friends/all")
    public ResponseEntity<?> getAllUserFriends(){
        return friendshipService.getAllUserFriends();
    }
}
