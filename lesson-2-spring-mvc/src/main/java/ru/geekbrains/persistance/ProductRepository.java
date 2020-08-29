package ru.geekbrains.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final Connection conn;

    @Autowired
    public ProductRepository(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public ProductRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into product(title, cost) values (?, ?);")) {
            stmt.setString(1, product.getTitle());
            stmt.setLong(2, product.getCost());
            stmt.execute();
        }
    }

    public Product findByTitle(String title) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, title, cost from product where title = ?")) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getLong(3));
            }
        }
        return new Product(-1, "None", 0L);
    }

    public Product findById(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, title , cost from product where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getLong(3));
            }
        }
        return new Product(-1, "None", 0L);
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update product set title = ?, cost = ? where id = ?")) {
            stmt.setString(1, product.getTitle());
            stmt.setLong(2, product.getCost());
            stmt.setLong(3, product.getId());
            stmt.execute();
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, title, cost from product");

            while (rs.next()) {
                res.add(new Product(rs.getInt(1), rs.getString(2), rs.getLong(3)));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists product (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    title varchar(25),\n" +
                    "    cost long\n" +
                    ");");
        }
    }

}
