package com.tcc.easyjobgo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.easyjobgo.factory.TokenFactory;
import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.repository.ITokenRepository;
import com.tcc.easyjobgo.repository.IUserRepository;
import com.tcc.easyjobgo.util.email.BuildEmailMessage;
import com.tcc.easyjobgo.util.email.EmailService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "easyjobgo/v1/user")
public class UserController {
    
    IUserRepository userRepository = UserFactory.createUserService();
    ITokenRepository tokenRepository = TokenFactory.createTokenService();

    private static final String IMG_PATH = System.getProperty("user.dir")+File.separator+"img"+File.separator;
    private static final String CONFIRMATION_LINK = "https://easyjobgoapp.herokuapp.com/easyjobgo/v1/registration/confirm?token=";

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

    @PostMapping(value="/registration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> saveUser(@RequestParam("user") String user, @RequestParam("file") MultipartFile profileImg){

        com.tcc.easyjobgo.model.Token confirmationToken = null;
        User savedUser = null;
        
        try {
            ObjectMapper om = new ObjectMapper();
            User responseUser = om.readValue(user, User.class);

            User usernameExists = userRepository.findByUsername(responseUser.getUsername());
            if(usernameExists != null) return new ResponseEntity<String>("Email já está cadastrado no Sistema!", HttpStatus.CONFLICT);
            
            User cpfExists = userRepository.findByCpf(responseUser.getCpf());
            if(cpfExists != null) return new ResponseEntity<String>("Cpf já está cadastrado no Sistema!", HttpStatus.CONFLICT);
            
            String profileImgRoot = IMG_PATH+responseUser.getCpf();
            File root = new File(profileImgRoot);
            if(!root.exists()) root.mkdirs();
            if(!profileImg.isEmpty()){
                byte[] bytes = profileImg.getBytes();
                Path filePath = Paths.get(root+File.separator+responseUser.getCpf()+"_perfil_"+profileImg.getOriginalFilename());
                Files.write(filePath, bytes);
                
                responseUser.setProfileImg(responseUser.getCpf()+"_perfil_"+profileImg.getOriginalFilename());
            }

            savedUser = userRepository.saveUser(responseUser);

            String token = UUID.randomUUID().toString();
            confirmationToken = new com.tcc.easyjobgo.model.Token(token, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), savedUser.getId());

            String link = CONFIRMATION_LINK + token;
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
