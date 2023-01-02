package com.upt.doccrypt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upt.doccrypt.model.User;
import lombok.Data;

/**
 * DTO class for user requests by ROLE_USER
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
//    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String isNotary;
    public User toUser(){
        User user = new User();
//        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
    public Boolean getIsNotary(){
        return isNotary.equals("true");
    }
}
