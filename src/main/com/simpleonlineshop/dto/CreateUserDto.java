package main.com.simpleonlineshop.dto;


import lombok.Builder;
import lombok.Value;
import main.com.simpleonlineshop.entity.Role;

import java.time.LocalDate;

@Value
@Builder
public class CreateUserDto {

    String id;
    String login;
    String password;
    String bithday;
    String role;
    String email;

}
