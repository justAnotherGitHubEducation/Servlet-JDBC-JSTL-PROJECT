package main.com.simpleonlineshop.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProductDto {

    String id;
    String name;
    String description;

}
