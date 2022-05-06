package main.com.simpleonlineshop.service;

import main.com.simpleonlineshop.dao.SaleDao;
import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.exception.ObjectNotFoundException;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.CreateSaleMapper;
import main.com.simpleonlineshop.mapper.UpdateSaleMapper;
import main.com.simpleonlineshop.validator.CreateSaleValidator;
import main.com.simpleonlineshop.validator.ValidationResult;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class SaleService {

    private static final SaleService INSTANCE = new SaleService();
    private final SaleDao saleDao = SaleDao.getInstance();
    private final CreateSaleMapper createSaleMapper = CreateSaleMapper.getInstance();
    private final UpdateSaleMapper updateSaleMapper = UpdateSaleMapper.getInstance();
    private final CreateSaleValidator createSaleValidator = CreateSaleValidator.getInstance();

    private SaleService() {
    }

    public static SaleService getInstance() {
        return INSTANCE;
    }

    public List<Sale> findAll() {

        return saleDao.findAll();
    }

    public Sale findById(Integer id) {

        return saleDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Sale not found( id "+ id+")"));
    }

    public Integer create(CreateSaleDto createSaleDto) {

        ValidationResult validationResult = createSaleValidator.isValid(createSaleDto);
        if (!validationResult.isValid())
            throw new ValidationException(validationResult.getErrors());

        Sale userEntity = createSaleMapper.mapFrom(createSaleDto);
        saleDao.save(userEntity);
        return userEntity.getId();
    }

    public boolean update(CreateSaleDto createSaleDto) {

        ValidationResult validationResult = createSaleValidator.isValid(createSaleDto);
        if (!validationResult.isValid())
            throw new ValidationException(validationResult.getErrors());
        Sale saleEntity = updateSaleMapper.mapFrom(createSaleDto);
        return saleDao.update(saleEntity);
    }

    public boolean delete(Integer id) {

        return saleDao.delete(id);
    }

}
