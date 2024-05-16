package com.filmbook.services.auth;

import com.filmbook.exceptions.AppException;
import com.filmbook.mapper.UserMapper;
import com.filmbook.model.database.User;
import com.filmbook.model.dto.CredentialsDto;
import com.filmbook.model.dto.SignUpDto;
import com.filmbook.model.dto.UserDto;
import com.filmbook.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("Nieznany użytkownik", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return UserMapper.INSTANCE.toUserDto(user);
        }
        throw new AppException("Niepoprawne hasło", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {



        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("Podana nazwa uzytkownika już istnieje", HttpStatus.BAD_REQUEST);
        }

        User user = UserMapper.INSTANCE.signUpToUser(userDto);
        user.setFollowing_count(0L);
        user.setFollowers_count(0L);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.toUserDto(savedUser);
    }



    public UserDto findByLogin(String login) {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new AppException("Nieznany użytkownik", HttpStatus.NOT_FOUND));
        return UserMapper.INSTANCE.toUserDto(user);
    }
}
