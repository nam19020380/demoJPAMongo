package com.example.demo.service;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.entity.Comment;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface CommentService {
    public void saveComment(Comment comment);

    public ResponseEntity<?> createComment(CommentRequest commentRequest);

    public ResponseEntity<?> editComment(CommentRequest commentRequest);

    public ResponseEntity<?> deleteComment(String id);

    public ResponseEntity<?> findByPostId(String statusId);

    public ResponseEntity<?> findByCommentId(String commentId);

    public void deleteByParentCommentId(String id);

    public void deleteByPostId(String id);

    public List<Comment> findByUserId(String userId);

    public Integer countByUserIdAndDateGreaterThanEqual(String userId, Date date);

    public Comment findById(String commentId);

    public Integer countByPostId(String postId);

    public Integer countById(String commentId);

    public Integer countByParentCommentId(String commentId);

    public Boolean existsByUserIdAndCommentId(String userId, String commentId);
}
