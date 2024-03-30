package com.learning.repository;

import com.learning.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDao implements Dao<Book> {
    @Override
    public Optional<Book> findById(long id) {
        Optional<Book> book = Optional.empty();
        String sqlQuery = "SELECT ID, TITLE FROM BOOK WHERE ID = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book result = new Book().setId(resultSet.getLong("id"))
                            .setTitle(resultSet.getString("title"));
                    book = Optional.of(result);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return book;
    }

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
