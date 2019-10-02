package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.User;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void removeBook(Book book);

    List<Book> findByGenre(Genre genre);

    List<Book> findByWriter(String writer);

    List<Book> safeSort(User user, Book... books);

    List<Book> findAll();

    List<Book> findSortByWriter(Book... books);

    List<Book> findSortByTitle(Book... books);
}
