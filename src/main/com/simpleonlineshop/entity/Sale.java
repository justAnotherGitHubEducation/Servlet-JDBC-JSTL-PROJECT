package main.com.simpleonlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Sale {

    private Integer id;
    private LocalDate date;
    private User user;
    private String description;

}
