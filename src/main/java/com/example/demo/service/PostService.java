package com.example.demo.service;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

public interface PostService {
    public void savePost(Post Post);

    public ResponseEntity<?> createPost(PostRequest postRequest);

    public ResponseEntity<?> editPost(PostRequest postRequest);

    public List<Post> findAll();

    public ResponseEntity<?> findByUserId(String userId);

    public ResponseEntity<?> deleteById(String id);

    public Post findByPostId(String id);

    public ResponseEntity<?> findPost(String id);

    public List<Post> findByUserIdAndDateOrderByPostId(String userId, Date date);

    public Integer countByUserIdAndDateGreaterThanEqual(String userId, Date date);

    public List<Post> findByUserIdOrderByDate(String userId);

    public List<Post> findFriendPostByUserId(String userId, Pageable pageable);

    public Boolean existsByUserIdAndPostId (String userId, String postId);
}
