package com.tcc.easyjobgo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tcc.easyjobgo.factory.TokenFactory;
import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.repository.ITokenRepository;
import com.tcc.easyjobgo.repository.IUserRepository;
import com.tcc.easyjobgo.util.email.BuildEmailMessage;
import com.tcc.easyjobgo.util.email.EmailService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "easyjobgo/v1/user")
public class UserController {
    
    IUserRepository userRepository = UserFactory.createUserService();
    ITokenRepository tokenRepository = TokenFactory.createTokenService();

    @Autowired
    EmailService emailSenderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> result = userRepository.findAll();
        if(result.size() > 0) return new ResponseEntity<List<User>>(result, HttpStatus.OK);
        return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(path="/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User result = userRepository.findByUsername(username);
        if(result != null) return new ResponseEntity<User>(result, HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/registration", consumes = {"application/json"})
    public ResponseEntity<String> saveUser(@RequestBody User user){
        com.tcc.easyjobgo.model.Token confirmationToken = null;
        User savedUser = null;
        
        try {
            User usernameExists = userRepository.findByUsername(user.getUsername());
            if(usernameExists != null) return new ResponseEntity<String>("Email já está cadastrado no Sistema!", HttpStatus.CONFLICT);
            
            User cpfExists = userRepository.findByCpf(user.getCpf());
            if(cpfExists != null) return new ResponseEntity<String>("Cpf já está cadastrado no Sistema!", HttpStatus.CONFLICT);
            
            savedUser = userRepository.saveUser(user);

            String token = UUID.randomUUID().toString();
            confirmationToken = new com.tcc.easyjobgo.model.Token(token, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), savedUser.getId());

            String link = "https://easyjobgoapp.herokuapp.com/easyjobgo/v1/registration/confirm?token=" + token;
            emailSenderService.send(savedUser.getUsername(), "Confirm your email", new BuildEmailMessage().confirmationEmail(savedUser.getFirstName(), link));

            tokenRepository.saveToken(confirmationToken);
        } catch (Exception e) {
            if(confirmationToken != null) tokenRepository.deleteToken(confirmationToken.getId());
            if(savedUser != null) userRepository.deleteUser(savedUser.getId());
            
            return new ResponseEntity<String>("Error While Try to Register User. Please, Try Again. [" + e.getMessage() + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Confirmation Token: " + confirmationToken.getToken(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value="/alteration", consumes = {"application/json"})
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User userExists = userRepository.findById(user.getId());
        if(userExists != null) return new ResponseEntity<User>(userRepository.updateUser(user), HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value="/delete", consumes = {"application/json"})
    public ResponseEntity<String> deleteUser(@RequestBody User user){
        User userExists = userRepository.findById(user.getId());
        if(userExists != null) return new ResponseEntity<String>(userRepository.deleteUser(user.getId()), HttpStatus.OK);
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    } 
}
