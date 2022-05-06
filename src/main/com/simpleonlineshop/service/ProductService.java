package main.com.simpleonlineshop.service;

import main.com.simpleonlineshop.dao.ProductDao;
import main.com.simpleonlineshop.dto.*;
import main.com.simpleonlineshop.entity.Product;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.exception.ObjectNotFoundException;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.CreateProductMapper;
import main.com.simpleonlineshop.mapper.UpdateProductMapper;
import main.com.simpleonlineshop.mapper.UpdateUserMapper;
import main.com.simpleonlineshop.validator.ValidationResult;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ProductService {

    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final CreateProductMapper createProductMapper = CreateProductMapper.getInstance();
    private final UpdateProductMapper updateProductMapper = UpdateProductMapper.getInstance();

    private ProductService() {

    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    public List<Product> findAll() {

        return productDao.findAll();
    }

    public Product findById(Integer id) {

        return productDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product not found( id "+ id+")"));
    }

    public Integer create(CreateProductDto createProductDto) {

        Product productEntity = createProductMapper.mapFrom(createProductDto);
        productDao.save(productEntity);
        return productEntity.getId();
    }

    public Integer update(CreateProductDto createProductDto) {

        Product productEntity = updateProductMapper.mapFrom(createProductDto);
        productDao.update(productEntity);
        return productEntity.getId();
    }

    public boolean delete(Integer id) {

        return productDao.delete(id);
    }
}





