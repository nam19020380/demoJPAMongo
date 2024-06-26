package com.example.demo.service;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.entity.Comment;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EmoteRepository emoteRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public ResponseEntity<?> createComment(CommentRequest commentRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Comment comment = new Comment();
            comment.setUserId(userDetails.getId());
            comment.setCreateDate(new Date());
            comment.setCreateUserName(userDetails.getUsername());
            if (commentRequest.getCommentId() != null) {
                comment.setPostId(commentRequest.getPostId());
                comment.setCommentId(commentRequest.getCommentId());
            } else {
                comment.setPostId(commentRequest.getPostId());
            }
            comment.setContent(commentRequest.getText());
            if (commentRequest.getFile() != null) {
                comment.setImageLink(imageService.uploadImage(commentRequest.getFile()));
            }
            comment.setDate(new Date());

            comment = commentRepository.save(comment);
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getCommentId());
            commentResponse.setOwnerName(userDetails.getUsername());
            commentResponse.setDate(comment.getDate());

            return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    public ResponseEntity<?> editComment(CommentRequest commentRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (commentRepository.existsByUserIdAndIdAndDelFlagIsFalse(userDetails.getId(), commentRequest.getId())) {
                Comment comment = new Comment();
                comment.setCommentId(commentRequest.getId());
                comment.setUserId(userDetails.getId());
                if (commentRequest.getCommentId() != null) {
                    comment.setCommentId(commentRequest.getCommentId());
                } else {
                    comment.setPostId(commentRequest.getPostId());
                }
                comment.setContent(commentRequest.getText());
                String old_address = commentRepository.findByIdAndDelFlagIsFalse(commentRequest.getId()).getImageLink();
                imageService.deleteImage(old_address);
                comment.setImageLink(imageService.uploadImage(commentRequest.getFile()));
                comment.setDate(new java.sql.Date(new java.util.Date().getTime()));
                comment.setUpdateUserName(userDetails.getUsername());
                comment.setUpdateDate(new Date());
                comment = commentRepository.save(comment);
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setId(comment.getCommentId());
                commentResponse.setOwnerName(userDetails.getUsername());
                commentResponse.setDate(comment.getDate());
                return new ResponseEntity<>(commentResponse, HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't edit other's comment");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    public ResponseEntity<?> deleteComment(String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (commentRepository.existsByUserIdAndIdAndDelFlagIsFalse(userDetails.getId(), id)) {
                String old_address = commentRepository.findByIdAndDelFlagIsFalse(id).getImageLink();
                imageService.deleteImage(old_address);
                Comment comment = commentRepository.findByIdAndDelFlagIsFalse(id);
                comment.setDelFlag(true);
                comment.setUpdateUserName(userDetails.getUsername());
                comment.setUpdateDate(new Date());
                commentRepository.save(comment);
                List<Comment> comments = commentRepository.findByCommentIdAndDelFlagIsFalse(id);
                for (Comment c : comments) {
                    deleteComment(c.getCommentId());
                }
                List<Emote> emotes = emoteRepository.findByCommentIdAndDelFlagIsFalse(id);
                for (Emote e : emotes) {
                    e.setDelFlag(true);
                    e.setUpdateUserName(userDetails.getUsername());
                    e.setUpdateDate(new Date());
                    emoteRepository.save(e);
                }
                return new ResponseEntity<>("Xoa comment thanh cong", HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't delete other's comment");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> findByPostId(String postId) {
        try {
            List<Comment> comments = commentRepository.findByPostIdAndDelFlagIsFalse(postId);
            List<CommentResponse> commentResponses = new ArrayList<>();
            for (Comment comment : comments) {
                commentResponses.add(new CommentResponse(comment.getCommentId(), userRepository.findById(comment.getUserId()).get().getUserName(),
                        comment.getContent(), comment.getDate(), emoteRepository.countByCommentIdAndDelFlagIsFalse(comment.getCommentId()),
                        commentRepository.countByCommentIdAndDelFlagIsFalse(comment.getCommentId())));
            }
            return ResponseEntity.ok()
                    .body(commentResponses);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findByCommentId(String commentId) {
        try {
            List<Comment> comments = commentRepository.findByCommentIdAndDelFlagIsFalse(commentId);
            List<CommentResponse> commentResponses = new ArrayList<>();
            for (Comment comment : comments) {
                commentResponses.add(new CommentResponse(comment.getCommentId(), userRepository.findById(comment.getUserId()).get().getUserName(),
                        comment.getContent(), comment.getDate(), commentRepository.countByCommentIdAndDelFlagIsFalse(commentId),
                        emoteRepository.countByCommentIdAndDelFlagIsFalse(commentId)));
            }
            return ResponseEntity.ok()
                    .body(commentResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteByParentCommentId(String id) {
        List<Comment> comments = commentRepository.findByCommentIdAndDelFlagIsFalse(id);
        for (Comment comment : comments) {
            deleteComment(comment.getCommentId());
        }
    }

    public void deleteByPostId(String id) {
        List<Comment> comments = commentRepository.findByPostIdAndDelFlagIsFalse(id);
        for (Comment comment : comments) {
            deleteComment(comment.getCommentId());
        }
    }

    public List<Comment> findByUserId(String userId) {
        return commentRepository.findByUserIdAndDelFlagIsFalse(userId);
    }

    public Integer countByUserIdAndDateGreaterThanEqual(String userId, Date date) {
        return commentRepository.countByUserIdAndDelFlagIsFalseAndDateGreaterThanEqual(userId, date);
    }

    public Comment findById(String commentId) {
        return commentRepository.findByIdAndDelFlagIsFalse(commentId);
    }

    public Integer countByPostId(String postId) {
        return commentRepository.countByPostIdAndDelFlagIsFalse(postId);
    }

    public Integer countById(String commentId) {
        return commentRepository.countByCommentIdAndDelFlagIsFalse(commentId);
    }

    public Integer countByParentCommentId(String commentId) {
        return commentRepository.countByCommentIdAndDelFlagIsFalse(commentId);
    }

    public Boolean existsByUserIdAndCommentId(String userId, String commentId) {
        return commentRepository.existsByUserIdAndIdAndDelFlagIsFalse(userId, commentId);
    }
}
