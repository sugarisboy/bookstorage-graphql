package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.TakenBook;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.ReturnBookDTO;

public interface UserService {

    User register(User user);

    void delete(User user);

    void lendBook(User user, Book book);

    ReturnBookDTO returnBook(TakenBook takenBook);

    long userAge(User user);
}
