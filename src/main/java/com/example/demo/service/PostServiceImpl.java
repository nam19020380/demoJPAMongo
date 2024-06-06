package com.example.demo.service;

import com.example.demo.dto.request.PostRequest;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.entity.Post;
import com.example.demo.respository.PostRepository;
import com.example.demo.sercurity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentService commentService;

    @Autowired
    EmoteService emoteService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public ResponseEntity<?> createPost(PostRequest postRequest){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Post post = new Post();
            post.setUserId(userDetails.getId());
            post.setContent(postRequest.getContent());
            post.setImageLink(imageService.uploadImage(postRequest.getFile()));
            post.setDate(new Date());
            post.setCreateDate(new Date());
            post.setCreateUserName(userDetails.getUsername());
            postRepository.save(post);
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getId());
            postResponse.setText(post.getContent());
            postResponse.setOwnerName(userDetails.getEmail());
            postResponse.setDate(post.getDate());
            postResponse.setCommentCount(commentService.countByPostId(post.getId()));
            postResponse.setEmoteCount(emoteService.countByPostId(post.getId()));
            return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> editPost(PostRequest postRequest){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String id = postRequest.getId();
            if(postRepository.existsByUserIdAndIdAndDelFlagIsFalse(userDetails.getId(), id)){
                Post post = new Post();
                post.setPostId(postRequest.getId());
                post.setUser(userService.findUserById(userDetails.getId()));
                post.setContent(postRequest.getContent());
                post.setImageLink(imageService.uploadImage(postRequest.getFile()));
                post.setDate(new java.sql.Date(new java.util.Date().getTime()));
                postRepository.save(post);
                PostResponse postResponse = new PostResponse();
                postResponse.setId(post.getPostId());
                postResponse.setText(post.getContent());
                postResponse.setOwnerName(post.getUser().getEmail());
                postResponse.setDate(post.getDate());
                postResponse.setCommentCount(commentService.countByPostPostId(post.getPostId()));
                postResponse.setEmoteCount(emoteService.countByPostPostId(post.getPostId()));
                return new ResponseEntity<>(postResponse, HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't edit other's postes");
            }
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public ResponseEntity<?> findByUserId(String userId){
        try{
            List<PostResponse> postResponses = new ArrayList<>();
            List<Post> postes = postRepository.findByUserUserId(userId);
            if(postes.isEmpty()){
                return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
            } else {
                for(Post post : postes){
                    PostResponse postResponse = new PostResponse();
                    postResponse.setId(post.getPostId());
                    postResponse.setText(post.getContent());
                    postResponse.setOwnerName(post.getUser().getEmail());
                    postResponse.setDate(post.getDate());
                    postResponse.setCommentCount(commentService.countByPostPostId(post.getPostId()));
                    postResponse.setEmoteCount(emoteService.countByPostPostId(post.getPostId()));
                    postResponses.add(postResponse);
                }
            }
            return new ResponseEntity<>(postResponses, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteById(String id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(postRepository.existsByUserUserIdAndPostId(userDetails.getId(), id)){
                Post post = postRepository.findByPostId(id);
                imageService.deleteImage(post.getImageLink());
                commentService.deleteByPostId(id);
                emoteService.deleteByPostId(id);
                postRepository.deleteByPostId(id);
                return new ResponseEntity<>("Xoa Post thanh cong", HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't delete other's postes");
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Post findByPostId(String id){
        return postRepository.findByPostId(id);
    }

    public ResponseEntity<?> findPost(String id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Post post = postRepository.findByPostId(id);
            PostResponse postResponse = new PostResponse();
            postResponse.setId(id);
            postResponse.setText(post.getContent());
            postResponse.setOwnerName(userDetails.getUsername());
            postResponse.setDate(post.getDate());
            postResponse.setCommentCount(commentService.countByPostPostId(id));
            postResponse.setEmoteCount(emoteService.countByPostPostId(id));
            return ResponseEntity.ok()
                    .body(postResponse);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<Post> findByUserIdAndDateOrderByPostId(String userId, Date date){
        return postRepository.findByUserUserIdAndDateOrderByPostId(userId, date);
    }

    public Integer countByUserIdAndDateGreaterThanEqual(String userId, Date date){
        return postRepository.countByUserUserIdAndDateGreaterThanEqual(userId, date);
    }

    public List<Post> findByUserIdOrderByDate(String userId){
        return postRepository.findByUserUserIdOrderByDate(userId);
    }

    public List<Post> findFriendPostByUserId(String userId, Pageable pageable){
        return postRepository.findFriendPostByUserId(userId, pageable);
    }

    public Boolean existsByUserIdAndPostId (String userId, String postId){
        return postRepository.existsByUserUserIdAndPostId(userId, postId);
    }
}
