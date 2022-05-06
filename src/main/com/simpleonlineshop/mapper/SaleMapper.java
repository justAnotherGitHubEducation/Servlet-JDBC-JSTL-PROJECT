package main.com.simpleonlineshop.mapper;

import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.entity.User;

public class SaleMapper implements Mapper<Sale, SaleDto> {

    private static final SaleMapper INSTANCE = new SaleMapper();

    public static SaleMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public SaleDto mapFrom(Sale object) {
        return SaleDto.builder()
                .id(object.getId())
                .date(object.getDate())
                .user_id(object.getUser().getId())
                .userLogin(object.getUser().getLogin())
                .description(object.getDescription()).build();

    }
}

