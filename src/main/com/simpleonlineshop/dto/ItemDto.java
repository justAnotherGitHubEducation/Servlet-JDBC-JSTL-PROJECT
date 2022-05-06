package main.com.simpleonlineshop.dto;

import lombok.Builder;
import lombok.Value;
import main.com.simpleonlineshop.entity.Product;

@Value
@Builder
public class ItemDto {

    Integer id;
    Integer product_id;
    String comment;
    Integer quantity;
    String productName;
    Integer sale_id;

}
