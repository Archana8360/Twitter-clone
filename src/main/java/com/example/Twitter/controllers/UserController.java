package com.example.Twitter.controllers;

import com.example.Twitter.models.ApplicationUser;
import com.example.Twitter.services.ImageService;
import com.example.Twitter.services.TokenService;
import com.example.Twitter.services.UserService;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ImageService imageService;

    @Autowired
    public UserController(UserService userService,TokenService tokenService,AuthenticationManager authenticationManager,ImageService imageService){
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.imageService = imageService;
    }

    @GetMapping("/verify")
    public ApplicationUser verifyIdentity(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        String username = "";
        ApplicationUser user;
        if(token.substring(0,6).equals("Bearer")){
            String strippedToken = token.substring(7);
            username = tokenService.getUsernameFromToken(strippedToken);
        }
        try{
            user = userService.getUserByUsername(username);
        }catch (Exception e){
            user = null;
        }
        return user;
    }

    @PostMapping("/pfp")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("image")MultipartFile file){
        String uploadImage = imageService.uploadImage(file,"pfp");

        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

}
