package main.com.simpleonlineshop.service;

import main.com.simpleonlineshop.dao.UserDao;
import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.dto.SaleDto;
import main.com.simpleonlineshop.dto.UserDto;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.exception.ObjectNotFoundException;
import main.com.simpleonlineshop.exception.ValidationException;
import main.com.simpleonlineshop.mapper.CreateUserMapper;
import main.com.simpleonlineshop.mapper.UpdateUserMapper;
import main.com.simpleonlineshop.mapper.UserMapper;
import main.com.simpleonlineshop.validator.CreateUserValidator;
import main.com.simpleonlineshop.validator.ValidationResult;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class UserService {

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UpdateUserMapper updateUserMapper = UpdateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<User> login(String login, String password) {

        return userDao.findByEmailAndPassword(login, password);
    }

    public User findById(Integer id) {

        return userDao.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found( id "+ id+")"));
    }

    public Integer create(CreateUserDto createUserDto) {

        ValidationResult validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid())
            throw new ValidationException(validationResult.getErrors());
        User userEntity = createUserMapper.mapFrom(createUserDto);
        userDao.save(userEntity);

        return userEntity.getId();
    }

    public Integer update(CreateUserDto createUserDto) {

        ValidationResult validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid())
            throw new ValidationException(validationResult.getErrors());
        User userEntity = updateUserMapper.mapFrom(createUserDto);
        userDao.update(userEntity);

        return userEntity.getId();
    }

    public List<User> findAll() {

        return userDao.findAll();
    }

    public boolean delete(Integer id) {

        return userDao.delete(id);
    }
}
