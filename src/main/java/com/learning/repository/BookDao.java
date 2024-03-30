package com.learning.repository;

import com.learning.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDao extends AbstractDao implements Dao<Book> {
    @Override
    public List<Book> findAll() {
        List<Book> books = Collections.emptyList();

        String sql = "SELECT * FROM BOOK";
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book().setId(resultSet.getLong("id"))
                        .setTitle(resultSet.getString("title"));
                books.add(book);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return books;
    }
}
