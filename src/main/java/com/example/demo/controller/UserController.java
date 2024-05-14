package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

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
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/all")
    public ResponseEntity<?> getAllUser() {
        try{
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
