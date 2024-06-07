package com.example.demo.controller;

import com.example.demo.dto.request.EmoteRequest;
import com.example.demo.service.CommentService;
import com.example.demo.service.EmoteService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/emote")
public class EmoteController {
    @Autowired
    EmoteService emoteService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<?> createEmote(@RequestBody EmoteRequest emoteRequest){
        return emoteService.saveEmote(emoteRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmote(@RequestParam String id){
        return emoteService.deleteById(id);
    }
}
