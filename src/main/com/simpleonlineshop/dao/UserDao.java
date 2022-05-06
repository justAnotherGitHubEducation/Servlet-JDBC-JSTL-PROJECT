package main.com.simpleonlineshop.dao;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDao implements Dao<Integer, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String SQL_INSERT = """
            INSERT INTO users (login, password, bithday, role, email) VALUES (?,?,?,?,?)        
            """;

    private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = """
            SELECT id,login, password, bithday, role, email FROM users WHERE login = ? AND password = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT id,login, password, bithday, role, email FROM users        
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT  id,login, password, bithday, role, email FROM users WHERE id = ?
            """;

    private static final String SQL_UPDATE = """
                UPDATE  users SET login = ?,password = ?,bithday = ?,role = ?,email = ?
                WHERE id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM users WHERE id = ?
                                        """;


    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public User save(User entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, entity.getLogin());
            preparedStatement.setObject(2, entity.getPassword());
            preparedStatement.setObject(3, entity.getBithday());
            preparedStatement.setObject(4, entity.getRole().name());
            preparedStatement.setObject(5, entity.getEmail());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;
        }
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String login, String password) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_FIND_BY_LOGIN_AND_PASSWORD)) {

            prepareStatement.setString(1, login);
            prepareStatement.setString(2, password);

            ResultSet resultSet = prepareStatement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = BuildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    private User BuildEntity(ResultSet resultSet) {
        return User.builder()
                .id(resultSet.getObject("id", Integer.class))
                .login(resultSet.getObject("login", String.class))
                .password(resultSet.getObject("password", String.class))
                .bithday(resultSet.getObject("bithday", LocalDate.class))
                .role(Role.find(resultSet.getObject("role", String.class)).orElse(null))
                .email(resultSet.getObject("email", String.class))
                .build();
    }

    @SneakyThrows
    @Override
    public List<User> findAll() {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_FIND_ALL)) {

            ResultSet resultSet = prepareStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(BuildEntity(resultSet));
            }
            return users;
        }
    }

    @SneakyThrows
    @Override
    public Optional<User> findById(Integer id) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = BuildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    @Override
    public boolean delete(Integer id) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {

            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public boolean update(User entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

            preparedStatement.setObject(1, entity.getLogin());
            preparedStatement.setObject(2, entity.getPassword());
            preparedStatement.setObject(3, entity.getBithday());
            preparedStatement.setObject(4, entity.getRole().name());
            preparedStatement.setObject(5, entity.getEmail());
            preparedStatement.setObject(6, entity.getId());

            return preparedStatement.executeUpdate() > 0;
        }
    }
}
