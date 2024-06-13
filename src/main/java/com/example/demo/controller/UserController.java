package com.example.demo.controller;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userService.saveUser(user);
            return ResponseEntity
                    .ok()
                    .body("Tao User moi thanh cong");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> changeUser(@RequestBody User user){
        try{
            userService.saveUser(user);
            return ResponseEntity
                    .ok()
                    .body("Edit User thanh cong");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam String email) {
        try{
            User user = userService.findUserByEmail(email);
            return new ResponseEntity<>(new UserResponse(user.getUserName(), user.getUserBirthday(), user.getUserAvatarLink(), user.getUserJob()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/all")
    public ResponseEntity<?> getAllUser() {
        try{
            List<User> userList = userService.findAll();
            List<UserResponse> userResponseList = userList.stream()
                    .map(t -> new UserResponse(t.getUserName(), t.getUserBirthday(), t.getUserAvatarLink(), t.getUserJob())).toList();
            return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/image/userId")
    public ResponseEntity<?> getImageByPostId(@RequestParam String id){
        try{
            User user = userService.findUserById(id);
            final ByteArrayResource inputStream = imageService.getImage(user.getUserAvatarLink());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
