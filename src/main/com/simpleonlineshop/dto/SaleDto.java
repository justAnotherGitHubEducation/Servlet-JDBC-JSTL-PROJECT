package main.com.simpleonlineshop.dto;

import lombok.Builder;
import lombok.Value;
import main.com.simpleonlineshop.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class SaleDto {
    int id;
    LocalDate date;
    int user_id;
    String description;
    String userLogin;
}
