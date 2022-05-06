package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dto.ItemDto;
import main.com.simpleonlineshop.dto.ProductDto;
import main.com.simpleonlineshop.entity.Item;
import main.com.simpleonlineshop.entity.Product;


public class ItemMapper implements Mapper<Item, ItemDto> {

    private static final ItemMapper INSTANCE = new ItemMapper();

    public static ItemMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ItemDto mapFrom(Item object) {
        return ItemDto.builder()
                        .id(object.getId())
                        .productName(object.getProduct().getName())
                        .product_id(object.getProduct().getId())
                        .comment(object.getComment())
                        .quantity(object.getQuantity())
                        .build();
    }
}