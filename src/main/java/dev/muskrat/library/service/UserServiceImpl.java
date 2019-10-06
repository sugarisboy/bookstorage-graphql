package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.TakenBook;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.ReturnBookDTO;
import dev.muskrat.library.exception.BookNotFoundException;
import dev.muskrat.library.repository.TakenBookRepository;
import dev.muskrat.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
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
    public void lendBook(User user, Book book) {
        if (book.getCount() < 1)
            throw new BookNotFoundException(
                String.format("Book %s not available", book.getTitle())
            );

        book.setCount(book.getCount() - 1);

        TakenBook takenBook = new TakenBook();
        takenBook.setUser(user);
        takenBook.setBook(book);
        takenBook.setExpired(
            Instant.now().plus(bookExpireDays, ChronoUnit.HOURS)
        );

        takenBookRepository.save(takenBook);
    }

    @Override
    public ReturnBookDTO returnBook(TakenBook takenBook) {
        Book book = takenBook.getBook();
        book.setCount(book.getCount() + 1);

        Instant now = Instant.now();
        Instant expired = takenBook.getExpired();

        long days = Duration.between(expired, now)
            .get(ChronoUnit.DAYS);
        double fine = days > 0 ? bookExpireFine * days : 0;

        takenBookRepository.delete(takenBook);

        return ReturnBookDTO.builder()
            .fine(fine)
            .build();
    }

    @Override
    public long userAge(User user) {
        Instant now = Instant.now();
        Instant expired = user.getBirthday();

        return ChronoUnit.YEARS.between(
            OffsetDateTime.ofInstant(expired, ZoneOffset.UTC),
            OffsetDateTime.ofInstant(now, ZoneOffset.UTC));
    }
}
