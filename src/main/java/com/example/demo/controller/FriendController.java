package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> acceptRequest(@RequestParam Integer id){
        return friendshipService.acceptRequest(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFriendship(@RequestParam Integer id){
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
