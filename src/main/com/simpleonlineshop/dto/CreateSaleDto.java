package main.com.simpleonlineshop.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;


@Value
@Builder
public class CreateSaleDto {

    String id;
    String date;
    String user_id;
    String description;

}

