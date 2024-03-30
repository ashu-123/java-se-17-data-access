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

    @Override
    public Book create(Book book) {

        String sqlQuery = "INSERT INTO BOOK (TITLE) VALUES (?)";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.executeUpdate();

            try (ResultSet genKeys = preparedStatement.getGeneratedKeys()) {
                if (genKeys.next()) {
                    book.setId(genKeys.getLong(1));
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String sqlQuery = "UPDATE BOOK SET TITLE = ? WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return book;
    }

    public int[] update(List<Book> books) {
        int[] records = {};
        String sqlQuery = "UPDATE BOOK SET TITLE = ?, RATING = ? WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            for(Book book : books) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setInt(2, book.getRating());
                preparedStatement.setLong(3, book.getId());

                preparedStatement.addBatch();
            }

            records = preparedStatement.executeBatch();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return records;
    }

    public int delete(Book book) {
        int rowsAffected = 0;
        String sqlQuery = "DELETE FROM BOOK WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, book.getId());
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return rowsAffected;
    }
}
