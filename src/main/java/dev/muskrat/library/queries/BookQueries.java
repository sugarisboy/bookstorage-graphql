package dev.muskrat.library.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import dev.muskrat.library.dao.Book;
import dev.muskrat.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookQueries implements GraphQLQueryResolver {

    private final BookService bookService;

    public Book book(Long id) {
        return bookService.findById(id);
    }

    public List<Book> books() {
        return bookService.findAll();
    }

    public List<Book> bookByWriter(String writer) {
        return bookService.findByWriter(writer);
    }
}
