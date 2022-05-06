package main.com.simpleonlineshop.dao;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SaleDao implements Dao<Integer, Sale> {

    private static final SaleDao INSTANCE = new SaleDao();

    private static final String SQL_FIND_ALL = """
            SELECT
                    sale.id,
                    date,                             
                    description,
                    u.id u_id,
                    u.login,
                    u.password,
                    u.bithday,
                    u.role,
                    u.email                    
                    FROM sale left join users u on sale.user_id = u.id
                        
            """;

    private static final String SQL_INSERT = """
            INSERT INTO sale(date,user_id,description) VALUES (?,?,?)
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT
                    sale.id,
                    date,                             
                    description,
                    u.id u_id,
                    u.login,
                    u.password,
                    u.bithday,
                    u.role,
                    u.email                    
                    FROM sale left join users u on sale.user_id = u.id
                    WHERE sale.id = ?
                        
            """;

    private static final String SQL_UPDATE = """
            UPDATE  sale SET date = ?,
                            user_id = ?,
                            description= ?
                            WHERE id = ?
                                        """;

    private static final String SQL_DELETE = """
            DELETE FROM sale WHERE id = ?
                                        """;


    private SaleDao() {
    }

    @SneakyThrows
    @Override
    public List<Sale> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Sale> sales = new ArrayList<>();

            while (resultSet.next()) {

                sales.add(buildSale(resultSet));

            }
            return sales;
        }
    }

    public static SaleDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public Optional<Sale> findById(Integer id) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Sale sale = null;

            while (resultSet.next()) {

                sale = buildSale(resultSet);
            }
            return Optional.ofNullable(sale);
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
    public boolean update(Sale entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setObject(2, entity.getUser().getId());
            preparedStatement.setObject(3, entity.getDescription());
            preparedStatement.setObject(4, entity.getId());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public Sale save(Sale entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setObject(2, entity.getUser().getId());
            preparedStatement.setObject(3, entity.getDescription());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;
        }
    }

    @SneakyThrows
    private Sale buildSale(ResultSet resultSet) {

            User user = User.builder()
                    .id(resultSet.getObject("u_id", Integer.class))
                    .login(resultSet.getObject("login", String.class))
                    .password(resultSet.getObject("password", String.class))
                    .bithday(resultSet.getObject("bithday", LocalDate.class))
                    .role(Role.find(resultSet.getObject("role", String.class)).orElse(null))
                    .build();

            return Sale.builder()

                    .id(resultSet.getObject("id", Integer.class))
                    .date(resultSet.getObject("date", LocalDate.class))
                    .user(user)
                    .description(resultSet.getObject("description", String.class))
                    .build();
    }
}
