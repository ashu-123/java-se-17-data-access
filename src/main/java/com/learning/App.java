package com.learning;

import com.learning.model.Book;
import com.learning.repository.BookDao;
import com.learning.repository.Dao;

import java.util.List;
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

        if(bookById.isPresent()) {
            bookById.map(book -> book.setTitle("Effective Java: Second Edition"))
                    .map(bookDao::update)
                    .ifPresent(book -> System.out.println("id = " + book.getId() + " title = " + book.getTitle()));
        }

        Book newBook = new Book().setTitle("Thomas H. Cormen");
        newBook = bookDao.create(newBook);

        System.out.println("Book created with id = " + newBook.getId());

        int recordsDeleted = bookDao.delete(newBook);
        System.out.println("Number of records deleted = " + recordsDeleted);

        books.forEach(book -> book.setRating(5));

        var records = bookDao.update(books);

        System.out.println(records.length);

        System.out.println("=============================");

        Book newBook2 = new Book().setTitle("Thomas H. Cormen");
        newBook2 = bookDao.create(newBook2);

        System.out.println("Book created with id = " + newBook2.getId());

        int[] deletedCount = bookDao.delete(List.of(newBook2));
        System.out.println("Number of records deleted = " + deletedCount.length);
    }
}
