package com.programming.techie.springngblog.service;

import com.programming.techie.springngblog.dto.AvatarDto;
import com.programming.techie.springngblog.dto.ChangeAvatar;
import com.programming.techie.springngblog.dto.LoginRequest;
import com.programming.techie.springngblog.dto.RegisterRequest;
import com.programming.techie.springngblog.model.Post;
import com.programming.techie.springngblog.model.User;
import com.programming.techie.springngblog.repository.UserRepository;
import com.programming.techie.springngblog.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.programming.techie.springngblog.service.RandomKey.getRandomNumberString;

@Service
public class AuthService {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    //-------------------------------------
    @Autowired
    private EmailSenderService service;
    public String vertifyEmail(RegisterRequest registerRequest){
        String emailsend = registerRequest.getEmail();
        String key=getRandomNumberString();
        service.sendSimpleEmail(emailsend,"Mã của bạn là : "+ key ,"Mã Xác Nhận ");
        return key;

    }
    //-----------------------------------
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    public List<String> getAllUsername(){
        List<User> users = userRepository.findAll();
        ArrayList<String> listUsername = new ArrayList<>();
       for(User i : users){
           listUsername.add(i.getUserName());
       }
       return listUsername;
    }
    public void changeAvatar(ChangeAvatar changeAvatar) {
        String loggedInUser = getCurrentUser().get().getUsername();
        Optional<User> oldUser = userRepository.findByUserName(loggedInUser);
        String avatar = changeAvatar.getAvatar();
        User user = mapAvatarToUser(avatar,oldUser);
        userRepository.save(user);
    }
    private User mapAvatarToUser(String avatar, Optional<User> oldUser) {
        User user = new User();
        user.setAvatar(avatar);
        user.setUserName(oldUser.get().getUserName());
        user.setEmail(oldUser.get().getEmail());
        user.setPassword(oldUser.get().getPassword());
        user.setId(oldUser.get().getId());
        return user;
    }
    public void changePassword(ChangeAvatar changePassword) {
        String loggedInUser = getCurrentUser().get().getUsername();
        Optional<User> oldUser = userRepository.findByUserName(loggedInUser);
        String password = changePassword.getPassword();
        User user = mapPasswordToUser(password,oldUser);
        userRepository.save(user);
    }
    private User mapPasswordToUser(String password, Optional<User> oldUser) {
        User user = new User();
        user.setPassword(encodePassword(password));
        user.setUserName(oldUser.get().getUserName());
        user.setEmail(oldUser.get().getEmail());
        user.setAvatar(oldUser.get().getAvatar());
        user.setId(oldUser.get().getId());
        return user;
    }


    public String getAvatar() {
        String loggedInUser = getCurrentUser().get().getUsername();
        Optional<User> oldUser = userRepository.findByUserName(loggedInUser);
        String avatar = oldUser.get().getAvatar();
        return avatar;
    }

    public List<AvatarDto> getAllAvatar(){
        List<AvatarDto> listAva = new ArrayList<>();
        List<User> listUser = userRepository.findAll();
        for(User i: listUser){
            AvatarDto j = new AvatarDto();
            j.setUsername(i.getUserName());
            j.setAvatar(i.getAvatar());
            listAva.add(j);
        }
        return listAva;
    }
}
