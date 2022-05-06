package main.com.simpleonlineshop.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateItemDto {

    String id;
    String product_id;
    String comment;
    String quantity;
    String productName;
    String sale_id;
}
