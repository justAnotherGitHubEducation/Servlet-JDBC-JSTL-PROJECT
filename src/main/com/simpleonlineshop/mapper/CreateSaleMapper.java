package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dao.UserDao;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.util.LocalDateFormatter;

public class CreateSaleMapper implements Mapper<CreateSaleDto, Sale> {

    private static final CreateSaleMapper INSTANCE = new CreateSaleMapper();

    private final UserDao userDao = UserDao.getInstance();


    private CreateSaleMapper() {
    }

    public static CreateSaleMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Sale mapFrom(CreateSaleDto object) {
        return Sale.builder()
                .date(LocalDateFormatter.format(object.getDate()))
                .user(userDao.findById(Integer.valueOf(object.getUser_id())).orElse(null))
                .description(object.getDescription())
                .build();
    }
}
