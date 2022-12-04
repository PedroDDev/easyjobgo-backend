package com.tcc.easyjobgo.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.repository.IUserRepository;

@RestController
@RequestMapping(path = "easyjobgo/v1")
public class LoginController {

    IUserRepository userRepository = UserFactory.createUserService();
    
    @GetMapping(value="/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm alg = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(alg).build();
                DecodedJWT decodeJWT = verifier.verify(refreshToken);
                String username = decodeJWT.getSubject();
                User user = userRepository.findByUsername(username);
                String accessToken = JWT.create()
                                        .withSubject(user.getUsername())
                                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                                        .withIssuer(request.getRequestURL().toString())
                                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                        .sign(alg);

                Map<String, String> tokens = new HashMap<>();

                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();

                error.put("error_message", e.getMessage());

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("RefreshToken is missing");
        }
    }
}
