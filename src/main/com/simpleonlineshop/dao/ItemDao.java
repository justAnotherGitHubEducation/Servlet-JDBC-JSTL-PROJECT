package main.com.simpleonlineshop.dao;

import lombok.SneakyThrows;
import main.com.simpleonlineshop.entity.Item;
import main.com.simpleonlineshop.entity.Product;
import main.com.simpleonlineshop.entity.Sale;
import main.com.simpleonlineshop.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ItemDao implements Dao<Integer, Item> {

    private static final ItemDao INSTANCE = new ItemDao();
    private static final SaleDao saleDao = SaleDao.getInstance();

    private static final String SQL_FIND_ALL_BY_SALE_ID = """
                SELECT 
                item.id,
                comment,
                quantity,
                item.sale_id,
                p.id pid,
                p.name pname,
                p.description pdescription
                FROM item left join product p on p.id = item.product_id 
                WHERE  item.sale_id = ?
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT 
            item.id,
            comment,
            quantity,
            item.sale_id,
            p.id pid,
            p.name pname,
            p.description pdescription
            FROM item left join product p on p.id = item.product_id 
            WHERE item.id = ?
                                
                    """;

    private static final String SQL_INSERT = """
               INSERT INTO item(product_id,comment,quantity,sale_id) VALUES (?,?,?,?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE  item SET product_id = ?,
                            comment = ?,
                            quantity= ?,
                            sale_id= ?
                            WHERE id = ?
                                        """;

    private static final String SQL_DELETE = """
            DELETE FROM item WHERE id = ?
            """;

    private ItemDao() {
    }

    public static ItemDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public List<Item> findAllBySaleId(Integer id) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_SALE_ID);
        ) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(buildItem(resultSet));
            }
            return items;
        }
    }

    @SneakyThrows
    private Item buildItem(ResultSet resultSet) {

        Product product = Product.builder()
                .id(resultSet.getObject("pid", Integer.class))
                .name(resultSet.getObject("pname", String.class))
                .description(resultSet.getObject("pdescription", String.class))
                .build();

        return Item.builder()
                .id(resultSet.getObject("id", Integer.class))
                .product(product)
                .comment(resultSet.getObject("comment", String.class))
                .quantity(resultSet.getObject("quantity", Integer.class))
                .sale(saleDao.findById(resultSet.getObject("sale_id", Integer.class)).orElse(null))
                .build();
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @SneakyThrows
    @Override
    public Optional<Item> findById(Integer id) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Item item = null;

            while (resultSet.next()) {

                item = buildItem(resultSet);
            }
            return Optional.ofNullable(item);
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
    public boolean update(Item entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

            preparedStatement.setObject(1, entity.getProduct().getId());
            preparedStatement.setObject(2, entity.getComment());
            preparedStatement.setObject(3, entity.getQuantity());
            preparedStatement.setObject(4, entity.getSale().getId());
            preparedStatement.setObject(5, entity.getId());

            return preparedStatement.executeUpdate() > 0;
        }

    }

    @SneakyThrows
    @Override
    public Item save(Item entity) {

        Connection connection = ConnectionManager.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, entity.getProduct().getId());
            preparedStatement.setObject(2, entity.getComment());
            preparedStatement.setObject(3, entity.getQuantity());
            preparedStatement.setObject(4, entity.getSale().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;

        }


    }
}
