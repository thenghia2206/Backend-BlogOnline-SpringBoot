package com.programming.techie.springngblog.service;

import com.programming.techie.springngblog.dto.CommentDto;
import com.programming.techie.springngblog.model.LikedComment;
import com.programming.techie.springngblog.repository.LikedCommentRepository;
import com.programming.techie.springngblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Service
public class LikedCommentService {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikedCommentRepository likedCommentRepository;
    @Autowired
    private CommentService commentService;
    @Transactional
    public void createLikedComment(Long comment_id, Long user_id) {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        LikedComment likedComment = mapFromDtoToLikedComment(comment_id, userRepository.findByUserName(loggedInUser.getUsername()).get().getId());
        likedCommentRepository.save(likedComment);
    }
    @Transactional
    public Long countNumLike(Long comment_id, Long user_id) {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        Long count = likedCommentRepository.countByCommentId(comment_id, userRepository.findByUserName(loggedInUser.getUsername()).get().getId());
        if (Objects.equals(count, 0L)) {
            createLikedComment(comment_id, user_id);
            CommentDto commentDto = commentService.readSingleComment(comment_id);
            commentService.updateNumLikedComment(comment_id, commentDto, 1L);
        }
        if (Objects.equals(count, 1L)) {
            deleteLikedComment(comment_id, user_id);
            CommentDto commentDto = commentService.readSingleComment(comment_id);
            commentService.updateNumLikedComment(comment_id, commentDto, -1L);
        }
        return null;
    }
    @Transactional
    public void deleteLikedComment(Long comment_id, Long user_id) {
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        likedCommentRepository.deleteByCommentIdAndUserId(comment_id, userRepository.findByUserName(loggedInUser.getUsername()).get().getId());
    }
    private LikedComment mapFromDtoToLikedComment(Long comment_id, Long user_id) {
        LikedComment likedComment = new LikedComment();
        likedComment.setComment_id(comment_id);
        likedComment.setUser_id(user_id);
        return likedComment;
    }
}
