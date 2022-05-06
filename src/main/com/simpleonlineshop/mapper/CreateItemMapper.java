package main.com.simpleonlineshop.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import main.com.simpleonlineshop.dao.ProductDao;
import main.com.simpleonlineshop.dao.SaleDao;
import main.com.simpleonlineshop.dto.CreateItemDto;
import main.com.simpleonlineshop.entity.Item;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.service.ProductService;
import main.com.simpleonlineshop.util.LocalDateFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateItemMapper implements Mapper<CreateItemDto, Item> {

    private static final CreateItemMapper INSTACE = new CreateItemMapper();

    private final ProductDao productDao = ProductDao.getInstance();
    private final SaleDao saleDao = SaleDao.getInstance();

    public static CreateItemMapper getInstance() {
        return INSTACE;
    }

    @Override
    public Item mapFrom(CreateItemDto object) {

        return Item.builder()
                .product(productDao.findById(Integer.valueOf(object.getProduct_id())).orElse(null))
                .comment(object.getComment())
                .quantity(Integer.valueOf(object.getQuantity()))
                .sale(saleDao.findById(Integer.valueOf(object.getSale_id())).orElse(null))
                .build();
    }
}
