package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dao.UserDao;
import main.com.simpleonlineshop.dto.CreateProductDto;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.entity.Product;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.util.LocalDateFormatter;

public class CreateProductMapper implements Mapper<CreateProductDto, Product> {

    private static final CreateProductMapper INSTANCE = new CreateProductMapper();

    private final UserDao userDao = UserDao.getInstance();


    private CreateProductMapper() {
    }

    public static CreateProductMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Product mapFrom(CreateProductDto object) {
        return Product.builder()
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }
}
