package main.com.simpleonlineshop.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.User;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();
    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .id(object.getId())
                .login(object.getLogin())
                //.password(object.getPassword())
                .bithday(object.getBithday())
                .role(object.getRole())
                .email(object.getEmail())
                .build();
    }
}
