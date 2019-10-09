package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.ReturnBookDTO;

public interface UserService {

    User register(User user);

    void delete(User user);

    void lendBook(User user, Book book);

    long userAge(User user);

    ReturnBookDTO returnBook(Long bookId, Long userId);
}
