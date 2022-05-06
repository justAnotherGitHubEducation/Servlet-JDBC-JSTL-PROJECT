package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dao.UserDao;
import main.com.simpleonlineshop.dto.CreateProductDto;
import main.com.simpleonlineshop.entity.Product;

public class UpdateProductMapper implements Mapper<CreateProductDto, Product> {

    private static final UpdateProductMapper INSTANCE = new UpdateProductMapper();
    private final UserDao userDao = UserDao.getInstance();


    private UpdateProductMapper() {
    }

    public static UpdateProductMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Product mapFrom(CreateProductDto object) {
        return Product.builder()
                .id(Integer.valueOf(object.getId()))
                .name(object.getName())
                .description(object.getDescription())
                .build();
    }
}
