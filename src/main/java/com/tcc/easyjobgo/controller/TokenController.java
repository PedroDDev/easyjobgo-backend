package com.tcc.easyjobgo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.easyjobgo.factory.TokenFactory;
import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.model.Token;
import com.tcc.easyjobgo.repository.ITokenRepository;
import com.tcc.easyjobgo.repository.IUserRepository;

@RestController
@RequestMapping(path = "easyjobgo/v1/registration")
public class TokenController {
    
    ITokenRepository tokenRepository = TokenFactory.createTokenService();
    IUserRepository userRepository = UserFactory.createUserService();

    @GetMapping(path ="confirm")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        Token confirmationToken = tokenRepository.findByToken(token);
        if(confirmationToken == null) return new ResponseEntity<String>("Confirmation Token not Found", HttpStatus.NOT_FOUND);

        if(confirmationToken.getConfirmationDate() != null){
            return new ResponseEntity<String>("Confirmation Token Alredy Confirmed!", HttpStatus.ALREADY_REPORTED);
        }

        LocalDateTime expiredDate = confirmationToken.getExpiresDate().toLocalDateTime();

        if(expiredDate.isBefore(LocalDateTime.now())){

            tokenRepository.deleteToken(confirmationToken.getId());
            userRepository.deleteUser(confirmationToken.getUserId());

            return new ResponseEntity<String>("Confirmation Token Expired. Please try again.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    
        confirmationToken.setConfirmationDate(Timestamp.valueOf(LocalDateTime.now()));
        tokenRepository.updateToken(confirmationToken);

        userRepository.updateUserStatus(confirmationToken.getUserId(), 1);

        return new ResponseEntity<String>("Confirmed!", HttpStatus.OK);
    }
}
