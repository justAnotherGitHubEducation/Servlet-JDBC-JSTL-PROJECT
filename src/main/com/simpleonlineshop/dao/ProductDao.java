package main.com.simpleonlineshop.dao;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.entity.Item;
import main.com.simpleonlineshop.entity.Product;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.entity.User;
import main.com.simpleonlineshop.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class ProductDao implements Dao<Integer, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    private static final String SQL_FIND_ALL = """
            SELECT
              id,
              name,
              description
              FROM product                         
            """;

    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + """
            WHERE id = ?      
            """;

    private static final String SQL_INSERT = """
            INSERT INTO product(name ,description) VALUES (?,?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE product SET name = ?,description = ?  
            WHERE id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM product
            WHERE id= ?
            """;

    private ProductDao() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public List<Product> findAll() {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(buildProduct(resultSet));
            }
            return products;
        }
    }

    @SneakyThrows
    private Product buildProduct(ResultSet resultSet) {

        return Product.builder().
                id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .description(resultSet.getObject("description", String.class))
                .build();
    }

    @SneakyThrows
    @Override
    public Optional<Product> findById(Integer id) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;

            while (resultSet.next()) {

                product = buildProduct(resultSet);

            }
            return Optional.ofNullable(product);

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
    public boolean update(Product entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getDescription());
            preparedStatement.setObject(3, entity.getId());

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public Product save(Product entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getDescription());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;

        }
    }
}
