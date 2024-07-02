package com.filmbook.controllers.auth;

import com.filmbook.config.JwtUtil;
import com.filmbook.config.UserAuthProvider;
import com.filmbook.model.dto.CredentialsDto;
import com.filmbook.model.dto.SignUpDto;
import com.filmbook.model.dto.UserDto;
import com.filmbook.services.auth.TokenService;
import com.filmbook.services.auth.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthenticationProvider;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;


    @Autowired
    public AuthController(UserService userService, UserAuthProvider userAuthenticationProvider, TokenService tokenService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        System.out.println("logowanie");
        UserDto userDto = userService.login(credentialsDto);
        System.out.println("po serwisie");
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getUsername()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        System.out.println("s ");
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getUsername()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getUserId())).body(createdUser);
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String tokenWithoutBearer = token.substring(7);
        if (jwtUtil.validateToken(tokenWithoutBearer)) {
            tokenService.invalidateToken(tokenWithoutBearer);
        }
        return ResponseEntity.noContent().build();
    }
}
