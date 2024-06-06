package com.example.demo.service;

import com.example.demo.dto.request.EmoteRequest;
import com.example.demo.entity.Emote;
import com.example.demo.respository.CommentRepository;
import com.example.demo.respository.EmoteRepository;
import com.example.demo.respository.PostRepository;
import com.example.demo.respository.UserRepository;
import com.example.demo.sercurity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class EmoteServiceImpl implements EmoteService{
    @Autowired
    EmoteRepository emoteRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> saveEmote(EmoteRequest emoteRequest){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Emote emote = new Emote();
            emote.setCreateUserName(userDetails.getUsername());
            emote.setCreateDate(new Date());
            if(emoteRequest.getCommentId() == null){
                if(!emoteRepository.existsByUserIdAndPostIdAndDelFlagIsFalse(userDetails.getId(), emoteRequest.getPostId())){
                    emote.setPostId(emoteRequest.getPostId());
                } else {
                    return new ResponseEntity<>("Already liked", HttpStatus.BAD_REQUEST);
                }
            } else {
                if(!emoteRepository.existsByUserIdAndCommentIdAndDelFlagIsFalse(userDetails.getId(), emoteRequest.getCommentId())){
                    emote.setCommentId(emoteRequest.getCommentId());
                } else {
                    return new ResponseEntity<>("Already liked", HttpStatus.BAD_REQUEST);
                }
            }
            emote.setUserId(userDetails.getId());
            emote.setDate(new java.sql.Date(new java.util.Date().getTime()));
            emoteRepository.save(emote);
            return new ResponseEntity<>("Tao emote thanh cong", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Emote> findByStatusId(String id){
        return emoteRepository.findByPostIdAndDelFlagIsFalse(id);
    }

    public List<Emote> findByCommentId(String id){
        return emoteRepository.findByCommentIdAndDelFlagIsFalse(id);
    }

    public void deleteByPostId(String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Emote> emotes = emoteRepository.findByPostIdAndDelFlagIsFalse(id);
        for(Emote e : emotes){
            e.setDelFlag(true);
            e.setUpdateUserName(userDetails.getUsername());
            e.setUpdateDate(new Date());
            emoteRepository.save(e);
        }
    }

    public void deleteByCommentId(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Emote> emotes = emoteRepository.findByCommentIdAndDelFlagIsFalse(id);
        for(Emote e : emotes){
            e.setDelFlag(true);
            e.setUpdateUserName(userDetails.getUsername());
            e.setUpdateDate(new Date());
            emoteRepository.save(e);
        }
    }

    public ResponseEntity<?> deleteById(String id) throws ResponseStatusException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(emoteRepository.existsByUserIdAndIdAndDelFlagIsFalse(userDetails.getId(), id)){
                Emote emote = emoteRepository.findById(id).get();
                emote.setDelFlag(true);
                emote.setUpdateUserName(userDetails.getUsername());
                emote.setUpdateDate(new Date());
                return new ResponseEntity<>("Xoa emote thanh cong", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You can't delete other's emote", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Integer countByUserIdAndDateGreaterThanEqual(String userId, Date date){
        return emoteRepository.countByUserIdAndDelFlagIsFalseAndDateGreaterThanEqual(userId, date);
    }

    public Integer countByPostId(String statusId){
        return emoteRepository.countByPostIdAndDelFlagIsFalse(statusId);
    }

    public Integer countByCommentId(String commentId){
        return emoteRepository.countByCommentIdAndDelFlagIsFalse(commentId);
    }

    public Boolean existsByUserIdAndEmoteId(String userId, String emoteId){
        return emoteRepository.existsByUserIdAndIdAndDelFlagIsFalse(userId, emoteId);
    }

    public Boolean existsByUserIdAndPostId(String userId, String statusId){
        return emoteRepository.existsByUserIdAndPostIdAndDelFlagIsFalse(userId, statusId);
    }

    public Boolean existsByUserIdAndCommentId(String userId, String commentId){
        return emoteRepository.existsByUserIdAndCommentIdAndDelFlagIsFalse(userId, commentId);
    }
}
