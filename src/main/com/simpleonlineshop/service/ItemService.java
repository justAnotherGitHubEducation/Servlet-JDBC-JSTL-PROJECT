package main.com.simpleonlineshop.service;

import main.com.simpleonlineshop.dao.ItemDao;
import main.com.simpleonlineshop.dto.CreateItemDto;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.dto.ItemDto;
import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.entity.Item;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.exception.ObjectNotFoundException;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.CreateItemMapper;
import main.com.simpleonlineshop.mapper.UpdateItemMapper;
import main.com.simpleonlineshop.validator.CreateItemValidator;
import main.com.simpleonlineshop.validator.ValidationResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ItemService {

    private static final ItemService INSTANCE = new ItemService();
    private final ItemDao itemDao = ItemDao.getInstance();
    private final CreateItemMapper createItemMapper = CreateItemMapper.getInstance();
    private final UpdateItemMapper updateItemMapper = UpdateItemMapper.getInstance();
    private final CreateItemValidator itemValidator = CreateItemValidator.getInstance();

    public static ItemService getInstance() {
        return INSTANCE;
    }

    private ItemService() {
    }

    public List<Item> findAllBySaleId(Integer id) {

        return itemDao.findAllBySaleId(id);
    }

    public Item findById(Integer id) {

        return itemDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Item not found( id "+ id+")"));
    }

    public Integer create(CreateItemDto createItemDto) {

        ValidationResult validationResult = itemValidator.isValid(createItemDto);

        if (!validationResult.isValid())
            throw new ValidationException(validationResult.getErrors());
        Item itemEntity = createItemMapper.mapFrom(createItemDto);
        itemDao.save(itemEntity);

        return itemEntity.getId();
    }

    public boolean update(CreateItemDto createItemDto) {

        ValidationResult validationResult = itemValidator.isValid(createItemDto);
        if (!validationResult.isValid())
            throw new ValidationException(validationResult.getErrors());
        Item itemEntity = updateItemMapper.mapFrom(createItemDto);

        return itemDao.update(itemEntity);
    }

    public boolean delete(Integer id) {

        return itemDao.delete(id);
    }

}
