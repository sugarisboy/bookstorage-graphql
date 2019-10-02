package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void removeBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return bookRepository.findAll().stream()
            .filter(book -> book.getGenre().equals(genre))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByWriter(String writer) {
        return bookRepository.findAll().stream()
            .filter(book -> book.getWriter().contains(writer))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> safeSort(User user, Book... books) {
        long age = userService.userAge(user);

        return Arrays.stream(books)
            .filter(book -> book.getAgeLimit() <= age)
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findSortByWriter(Book... books) {
        return Arrays.stream(books)
            .sorted(Comparator.comparing(Book::getWriter))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findSortByTitle(Book... books) {
        return Arrays.stream(books)
            .sorted(Comparator.comparing(Book::getTitle))
            .collect(Collectors.toList());
    }
}
