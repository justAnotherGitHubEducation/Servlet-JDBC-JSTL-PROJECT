package main.com.simpleonlineshop.entity;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Role {
    ADMIN,
    USER;

    public static Optional<Role> find(String gender) {

        return Arrays.stream(values()).filter(it -> it.name().equals(gender))
                .findFirst();
    }
}
