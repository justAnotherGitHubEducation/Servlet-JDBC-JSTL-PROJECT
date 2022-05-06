package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dto.ProductDto;
import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.entity.Product;
import main.com.simpleonlineshop.entity.Sale;

public class ProductMapper implements Mapper<Product, ProductDto> {

    private static final ProductMapper INSTANCE = new ProductMapper();

    public static ProductMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ProductDto mapFrom(Product object) {
        return ProductDto.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }
}
