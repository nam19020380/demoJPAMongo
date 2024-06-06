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

    public void deleteByStatusId(String id);

    public void deleteByCommentId(String id);

    public Integer countByUserUserIdAndDateGreaterThanEqual(String userId, Date date);

    public Integer countByStatusStatusId(String statusId);

    public Integer countByCommentCommentId(String commentId);

    public Boolean existsByUserUserIdAndEmoteId(String userId, String emoteId);

    public Boolean existsByUserUserIdAndStatusStatusId(String userId, String statusId);

    public Boolean existsByUserUserIdAndCommentCommentId(String userId, String commentId);
}
