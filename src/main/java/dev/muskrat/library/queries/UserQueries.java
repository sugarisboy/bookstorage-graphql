package dev.muskrat.library.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.service.BookService;
import dev.muskrat.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserQueries implements GraphQLQueryResolver {

    private final UserService userService;

    public List<User> users() {
        return userService.findAll();
    }
}
