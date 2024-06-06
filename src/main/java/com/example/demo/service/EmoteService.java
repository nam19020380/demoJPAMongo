package com.example.demo.service;

import com.example.demo.dto.request.EmoteRequest;
import com.example.demo.entity.Emote;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface EmoteService {
    public ResponseEntity<?> saveEmote(EmoteRequest emoteRequest);

    public ResponseEntity<?> deleteById(String id);

    public List<Emote> findByStatusId(String id);

    public List<Emote> findByCommentId(String id);

    public void deleteByPostId(String id);

    public void deleteByCommentId(String id);

    public Integer countByUserIdAndDateGreaterThanEqual(String userId, Date date);

    public Integer countByPostId(String statusId);

    public Integer countByCommentId(String commentId);

    public Boolean existsByUserIdAndEmoteId(String userId, String emoteId);

    public Boolean existsByUserIdAndPostId(String userId, String statusId);

    public Boolean existsByUserIdAndCommentId(String userId, String commentId);
}
