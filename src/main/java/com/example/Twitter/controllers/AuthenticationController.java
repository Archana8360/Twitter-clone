package com.example.Twitter.controllers;

import com.example.Twitter.dto.FindUsernameDTO;
import com.example.Twitter.exceptions.*;
import com.example.Twitter.models.ApplicationUser;
import com.example.Twitter.models.LoginResponse;
import com.example.Twitter.models.Registration;
import com.example.Twitter.services.TokenService;
import com.example.Twitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService,TokenService tokenService,AuthenticationManager authenticationManager){
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailTaken(){
        return new ResponseEntity<String>("The email you provided is already registered", HttpStatus.CONFLICT);
    }


    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody Registration ro){
        return userService.registerUser((ro));
    }


    @ExceptionHandler({UserDoesNotExistException.class})
        public ResponseEntity<String> handleUserDoesntExist(){
            return new ResponseEntity<String>("The user you are looking for doesnot exist", HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/phone")
    public ApplicationUser updatePhoneNumber(@RequestBody LinkedHashMap<String,String>body){
        String username = body.get("username");
        String phone = body.get("phone");

        ApplicationUser user = userService.getUserByUsername(username);
        user.setPhone(phone);

        return userService.updateUser(user);

    }

    @ExceptionHandler({EmailFailedToSendException.class})
    public ResponseEntity<String> handleFailedEmail(){
        return new ResponseEntity<String>("The email failed to send, try again later", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PostMapping("/email/code")
    public ResponseEntity<String> createEmailVerification(@RequestBody LinkedHashMap<String,String>body){
        userService.generateEmailVerification(body.get("username"));
        return new ResponseEntity<String>("Verification code generated, email sent",HttpStatus.OK);
    }

    @ExceptionHandler({IncorrectVerificationCodeException.class})
    public ResponseEntity<String> incorrectCodeHandler(){
        return new ResponseEntity<String>("The code provided doesnot match the users code",HttpStatus.CONFLICT);
    }

    @PostMapping("/email/verify")
    public ApplicationUser verifyEmail(@RequestBody LinkedHashMap<String, String> body){

        Long code = Long.parseLong(body.get("code"));
        String username = body.get("username");
        return userService.verifyEmail(username,code);
    }

    @PutMapping("/update/password")
    public ApplicationUser updatePassword(@RequestBody LinkedHashMap<String,String>body){
        String username = body.get("username");
        String password = body.get("password");
        return userService.setPassword(username, password);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LinkedHashMap<String,String> body) throws InvalidCredentialsException{

        String username = body.get("username");
        String password = body.get("password");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password));
            String token = tokenService.generateToken(auth);
            return new LoginResponse(userService.getUserByUsername(username),token);

        } catch(AuthenticationException e){
            throw new InvalidCredentialsException();
        }
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<String> handleInvalidCredentials(){
        return new ResponseEntity<String>("Invalid credentials", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/find")
    public ResponseEntity<String> verifyUsername(@RequestBody FindUsernameDTO credential){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        String username = userService.verifyUsername(credential);
        return new ResponseEntity<String>(username, HttpStatus.OK);
    }


}
