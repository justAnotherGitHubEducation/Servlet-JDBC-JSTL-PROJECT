package main.com.simpleonlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Item {

    private Integer id;
    private Product product;
    private String comment;
    private Integer quantity;
    private Sale sale;

}
