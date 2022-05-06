package main.com.simpleonlineshop.dto;


import lombok.Builder;
import lombok.Value;
import main.com.simpleonlineshop.entity.Role;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {

    Integer id;
    String login;
    String password;
    LocalDate bithday;
    Role role;
    String email;

}
