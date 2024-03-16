package com.filmbook.mapper;

import com.filmbook.model.database.User;
import com.filmbook.model.dto.SignUpDto;
import com.filmbook.model.dto.UserDto;
import org.springframework.web.bind.annotation.Mapping;

//@Mapper(componentModel = "spring", uses = UserService.class)
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

//    @Mapping(target = "password", ignore = true)
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto userDto);
}
