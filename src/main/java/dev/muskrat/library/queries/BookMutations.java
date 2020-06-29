package dev.muskrat.library.queries;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import dev.muskrat.library.dao.Book;
import dev.muskrat.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMutations implements GraphQLMutationResolver {

    private final BookService bookService;

    public Book newBook(Book book) {
        return bookService.addBook(book);
    }

    public Boolean deleteBook(Long id) {
        return bookService.deleteBook(id);
    }

    public Book updateBook(Long id, Book book) {
        return bookService.updateBook(id, book);
    }
}
