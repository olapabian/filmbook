package com.filmbook.controllers.users;

import com.filmbook.config.UserAuthProvider;
import com.filmbook.model.requestsToSend.UserInfo;
import com.filmbook.services.userOperations.UserInfoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {
    private final UserInfoService userInfoService;
    private final UserAuthProvider userAuthProvider;

    public UserController(UserInfoService userInfoService, UserAuthProvider userAuthProvider) {
        this.userInfoService = userInfoService;
        this.userAuthProvider = userAuthProvider;
    }

    @GetMapping("/user")
    public UserInfo getUser(Authentication authentication) {
        UserDetails userDetails = userAuthProvider.getUserDetails(authentication);
        return userInfoService.getUserInfo(userDetails.getUsername());
    }

    @GetMapping("/userInfoByUsername/{username}")
    public UserInfo getUserInfoByUsername(@PathVariable String username) {
        return userInfoService.getUserInfo(username);
    }

    @GetMapping("/userInfoById/{userId}")
    public UserInfo getUserInfoById(@PathVariable String userId) {
        return userInfoService.getUserInfoById(Long.parseLong(userId));
    }

    @GetMapping("/userImageByUsername/{username}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String username) {
        byte[] imageBytes = userInfoService.getUserImageByUsername(username);
        if (imageBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userImageById/{id}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long id) {
        byte[] imageBytes = userInfoService.getUserImageById(id);
        if (imageBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/insertUserImageById/{id}")
    public void insertUserImageById(@PathVariable Long id, @RequestBody byte[] img) throws Exception {
        userInfoService.insertUserImageById(id, img);
    }

    //    @PostMapping("/deleteUserImageById/{id}")
//    public void deleteUserImageById(@PathVariable Long id){
//        userInfoService.deleteUserImageById(id);
//    }
    @DeleteMapping("/deleteUserImageById/{id}")
    public ResponseEntity<String> deleteUserImageById(@PathVariable Long id) {
        try {
            boolean isDeleted = userInfoService.deleteUserImageById(id);
            if (isDeleted) {
                return ResponseEntity.ok("Zdjęcie zostało pomyślnie usunięte");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas usuwania zdjęcia");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas usuwania zdjęcia");
        }
    }



}
