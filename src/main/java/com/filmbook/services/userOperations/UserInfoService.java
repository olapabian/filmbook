package com.filmbook.services.userOperations;

import com.filmbook.model.database.User;
import com.filmbook.model.encoder.ImageUtils;
import com.filmbook.model.requestsToSend.UserInfo;
import com.filmbook.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;

    public UserInfo getUserInfo(String username) {
        User user = null;
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        return new UserInfo(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getGender(), user.getFriendsIds(), user.getFollowing_ids(),user.getFollowers_ids(),user.getFollowers_count(),user.getFollowing_count());
    }
    public UserInfo getUserInfoById(Long id) {
        User user = null;
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        return new UserInfo(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getGender(), user.getFriendsIds(), user.getFollowing_ids(),user.getFollowers_ids(),user.getFollowers_count(),user.getFollowing_count());
    }
    public byte[] getUserImageByUsername(String username) {
        byte[] userImage = userRepository.findImageByUsername(username);
        return userImage;
    }

    public byte[] getUserImageById(Long id) {
        byte[] userImage = userRepository.findImageById(id);
        return userImage;
    }

    public void insertUserImageById(Long id, byte[] img) throws IOException {
        byte[] encodedImage = ImageUtils.encodeImageToJpg(img);
        userRepository.insertUserImageById(id, encodedImage);
    }

    @Transactional
    public boolean deleteUserImageById(Long id) {
        try {
            userRepository.deleteUserImageById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
