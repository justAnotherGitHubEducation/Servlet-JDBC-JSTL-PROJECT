package main.com.simpleonlineshop.dto;

import lombok.Builder;
import lombok.Value;
import main.com.simpleonlineshop.entity.Product;

@Value
@Builder
public class ProductDto {

    private Integer id;
    private String name;
    private String description;

}
