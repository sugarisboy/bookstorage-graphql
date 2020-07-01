package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.TakenBook;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.ReturnBookDTO;
import dev.muskrat.library.exception.BadRequestException;
import dev.muskrat.library.exception.BookNotFoundException;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.TakenBookRepository;
import dev.muskrat.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final TakenBookRepository takenBookRepository;

    @Value("${app.expire.book.days}")
    private int bookExpireDays;

    @Value("${app.expire.book.fine}")
    private double bookExpireFine;

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void lendBook(User user, Book book) throws BookNotFoundException {
        if (book.getCount() < 1)
            throw new BookNotFoundException(
                String.format("Book %s not available", book.getTitle())
            );

        book.setCount(book.getCount() - 1);
        bookRepository.save(book);

        Instant now = Instant.now();

        TakenBook takenBook = new TakenBook();
        takenBook.setUser(user);
        takenBook.setBook(book);
        takenBook.setCreated(now.truncatedTo(ChronoUnit.DAYS));
        takenBook.setExpired(now.plus(bookExpireDays, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS));

        takenBookRepository.save(takenBook);
    }

    @Override
    public ReturnBookDTO returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
            () -> new BadRequestException("Book with id " + bookId + " not found")
        );

        List<TakenBook> usersTakeBook = book.getUsers();
        TakenBook takenBook = usersTakeBook.stream()
            .filter(b -> b.getUser().getId() == userId)
            .min((a, b) -> a.getExpired().getNano() < b.getExpired().getNano() ? 1 : -1)
            .orElseThrow(
                () -> new BadRequestException(userId + " don't lend book with id " + bookId)
            );

        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant expired = takenBook.getExpired().truncatedTo(ChronoUnit.DAYS);
        long daysFine = betweenInstant(ChronoUnit.DAYS, expired, now);
        double fine = daysFine > 0 ? daysFine * bookExpireFine : 0;

        takenBookRepository.delete(takenBook);
        book.setCount(book.getCount() + 1);

        return ReturnBookDTO.builder()
            .fine(fine)
            .build();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public long userAge(User user) {
        Instant now = Instant.now();
        Instant expired = user.getBirthday();

        return ChronoUnit.YEARS.between(
            OffsetDateTime.ofInstant(expired, ZoneOffset.UTC),
            OffsetDateTime.ofInstant(now, ZoneOffset.UTC));
    }

    private long betweenInstant(ChronoUnit chronoUnit, Instant start, Instant end) {
        return chronoUnit.between(
            OffsetDateTime.ofInstant(start, ZoneOffset.UTC),
            OffsetDateTime.ofInstant(end, ZoneOffset.UTC));
    }
}
