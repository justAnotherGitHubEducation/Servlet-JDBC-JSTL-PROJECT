package main.com.simpleonlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class Product {

    private Integer id;
    private String name;
    private String description;

}
