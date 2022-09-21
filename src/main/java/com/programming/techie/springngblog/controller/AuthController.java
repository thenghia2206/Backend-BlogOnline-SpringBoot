package com.programming.techie.springngblog.controller;

import com.programming.techie.springngblog.dto.*;
import com.programming.techie.springngblog.model.User;
import com.programming.techie.springngblog.service.AuthService;
import com.programming.techie.springngblog.service.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
//    @Autowired
//    private AuthService authService;

    //    @PostMapping("/signup")
//    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
//        authService.signup(registerRequest);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//----------------------------------------------------------
    @Autowired
    private AuthService authService;

    String keys;

    private RegisterRequest registerRequestAll;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        keys=authService.vertifyEmail(registerRequest);
        registerRequestAll=registerRequest;
//        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/vertify")
    public  ResponseEntity vertify(@RequestBody VertifyRequest vertifyRequest){
        String key= vertifyRequest.getKey();
        if(keys.equals(key)){
            authService.signup(registerRequestAll);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }
//-------------------------------------
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<String>> getAllUser(){
        return new ResponseEntity<>(authService.getAllUsername(), HttpStatus.OK);
    }
    @PutMapping("/change/avatar")
    public ResponseEntity changeAvatar(@RequestBody ChangeAvatar changeAvatar) {
        authService.changeAvatar(changeAvatar);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/change/password")
    public ResponseEntity changePassword(@RequestBody ChangeAvatar changePassword) {
        authService.changePassword(changePassword);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/getAvatar")
    public ResponseEntity getAvatar(){
        return new ResponseEntity(authService.getAvatar(), HttpStatus.OK);
    }

    @GetMapping("/getAllAvatar")
    public ResponseEntity<List<AvatarDto>> getAllAvatar(){
        return new ResponseEntity<>(authService.getAllAvatar(), HttpStatus.OK);
    }

}
