package com.example.demo.controller;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    ImageService imageService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createComment(@ModelAttribute CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editComment(@ModelAttribute CommentRequest commentRequest) {
        return commentService.editComment(commentRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam String id) {
        return commentService.deleteComment(id);
    }

    @GetMapping(value = "/post")
    public ResponseEntity<?> findByPostId(@RequestParam String id) {
        return commentService.findByPostId(id);
    }

    @GetMapping(value = "/comment")
    public ResponseEntity<?> findByCommentId(@RequestParam String id) {
        return commentService.findByCommentId(id);
    }

    @GetMapping(value = "/image/commentId")
    public ResponseEntity<?> getImageByCommentId(@RequestParam String id){
        try{
            Comment comment = commentService.findById(id);
            final ByteArrayResource inputStream = imageService.getImage(comment.getImageLink());
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
