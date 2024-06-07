package com.example.demo.controller;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.entity.Post;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    EmoteService emoteService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(@ModelAttribute PostRequest postRequest){
        return postService.createPost(postRequest);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllUserPost(@RequestParam String id){
        return postService.findByUserId(id);
    }

    @GetMapping(value = "/postId")
    public ResponseEntity<?> getPostById(@RequestParam String id){
        return postService.findPost(id);
    }

    @GetMapping(value = "/image/postId")
    public ResponseEntity<?> getImageByPostId(@RequestParam String id){
        try{
            Post post = postService.findByPostId(id);
            final ByteArrayResource inputStream = imageService.getImage(post.getImageLink());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editPost(@RequestBody PostRequest postRequest){
        return postService.editPost(postRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam String id){
        return postService.deleteById(id);
    }
}
