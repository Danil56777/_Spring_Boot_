package ru.urfu.TestSecurity2dbThemeleafApplication.service;

import ru.urfu.TestSecurity2dbThemeleafApplication.dto.UserDto;
import ru.urfu.TestSecurity2dbThemeleafApplication.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findByEmail(String email);
    List<UserDto> findAllUsers();
}
