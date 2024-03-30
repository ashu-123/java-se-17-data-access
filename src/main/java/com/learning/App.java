package com.learning;

import com.learning.model.Book;
import com.learning.repository.BookDao;
import com.learning.repository.Dao;

import java.util.Optional;

public class App {
    public static void main(String[] args) {
        Dao<Book> bookDao = new BookDao();
        var books = bookDao.findAll();

        books.forEach(book -> {
            System.out.println("id = " + book.getId() + " title = " + book.getTitle());
        });

        var bookById = bookDao.findById(1);
        bookById.ifPresent(book -> System.out.println("id = " + book.getId() + " title = " + book.getTitle()));

        Book newBook = new Book().setTitle("Thomas H. Cormen");
        newBook = bookDao.create(newBook);

        books.forEach(book -> {
            System.out.println("id = " + book.getId() + " title = " + book.getTitle());
        });

    }
}
