
package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dao.UserDao;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.util.LocalDateFormatter;

public class UpdateSaleMapper implements Mapper<CreateSaleDto, Sale> {

    private static final UpdateSaleMapper INSTANCE = new UpdateSaleMapper();
    private final UserDao userDao = UserDao.getInstance();

    private UpdateSaleMapper() {
    }

    public static UpdateSaleMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Sale mapFrom(CreateSaleDto object) {
        return Sale.builder()
                .id(Integer.valueOf(object.getId()))
                .date(LocalDateFormatter.format(object.getDate()))
                .user(userDao.findById(Integer.valueOf(object.getUser_id())).orElse(null))
                .description(object.getDescription())
                .build();
    }
}

