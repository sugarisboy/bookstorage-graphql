package dev.muskrat.library.queries;

import com.coxautodev.graphql.tools.GraphQLResolver;
import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.TakenBook;
import dev.muskrat.library.dao.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookResolver implements GraphQLResolver<Book> {

    public List<User> getUsers(Book book) {
        return book.getUsers().stream()
            .map(TakenBook::getUser)
            .collect(Collectors.toList());
    }
}
