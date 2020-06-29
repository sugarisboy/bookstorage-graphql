package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void removeBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
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
            .filter(book -> book.getWriter().toLowerCase().contains(writer.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> safeSort(User user, List<Book> books) {
        long age = userService.userAge(user);

        return books.stream()
            .filter(book -> book.getAgeLimit() <= age)
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findSortByWriter(List<Book> books) {
        return books.stream()
            .sorted(Comparator.comparing(Book::getWriter))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findSortByTitle(List<Book> books) {
        return books.stream()
            .sorted(Comparator.comparing(Book::getTitle))
            .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteBook(Long id) {
        Optional<Book> optional = bookRepository.findById(id);
        optional.ifPresent(book -> {
            book.setDeleted(true);
            bookRepository.save(book);
        });
        return optional.isPresent();
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book storedBook = bookRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Book with id not found")
        );

        if (book.getWriter() != null) {
            storedBook.setWriter(book.getWriter());
        }
        if (book.getTitle() != null) {
            storedBook.setTitle(book.getTitle());
        }
        if (book.getCount() != null) {
            storedBook.setCount(book.getCount());
        }

        return bookRepository.save(storedBook);
    }
}
