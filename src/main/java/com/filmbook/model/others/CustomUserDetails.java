package com.filmbook.model.others;

import com.filmbook.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private final UserDto userDto;

    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String token;
    private final String password;
    private final String gender;
    private final byte[] userImage;
    private final String friendsIds;
    public CustomUserDetails(UserDto userDto) {
        this.userDto = userDto;
        this.userId = userDto.getUserId();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.username = userDto.getUsername();
        this.gender = userDto.getGender();
        this.userImage = userDto.getUserImage();
        this.friendsIds = userDto.getFriendsIds();
        this.password = userDto.getPassword();
        this.token = userDto.getToken();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
