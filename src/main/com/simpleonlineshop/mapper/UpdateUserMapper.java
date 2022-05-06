package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.util.LocalDateFormatter;

public class UpdateUserMapper implements Mapper<CreateUserDto, User> {

    private static final UpdateUserMapper INSTANCE = new UpdateUserMapper();

    private UpdateUserMapper() {
    }

    public static UpdateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto object) {

        return User.builder()
                .id(Integer.valueOf(object.getId()))
                .login(object.getLogin())
                .password(object.getPassword())
                .bithday(LocalDateFormatter.format(object.getBithday()))
                .role(Role.find(object.getRole()).get())
                .email(object.getEmail())
                .build();
    }
}
