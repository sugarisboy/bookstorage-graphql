package dev.muskrat.library.menu.commands;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.exception.BadRequestException;
import dev.muskrat.library.menu.CommandMenu;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.UserRepository;
import dev.muskrat.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksCommand extends CommandMenu {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    public BooksCommand() {
        super("books");
    }

    @Override
    public void run(String[] args)  {
        List<Book> books = bookRepository.findAll();

        if (args.length == 1) {
            print(books);
        } else if (args.length == 4) {
            String user_id = args[1];
            if (!user_id.matches("[0-9]+"))
                throw new BadRequestException("User id is not valid data");
            Long userId = Long.valueOf(user_id);
            User user = userRepository.findById(userId).orElseThrow(
                () -> new BadRequestException("User with id " + user_id + " not found")
            );

            books = bookService.safeSort(user, books);

            if (args[2].equalsIgnoreCase("sort")) {
                if (args[3].equalsIgnoreCase("writer")) {
                    print(bookService.findSortByWriter(books));
                } else if (args[3].equalsIgnoreCase("title")) {
                    print(bookService.findSortByTitle(books));
                } else {
                    print(books);
                }
            }
        }
    }

    private void print(List<Book> books) {
        System.out.printf("%3s %20s %30s %15s %5s %5s\n",
            "id",
            "writer",
            "title",
            "genre",
            "age",
            "count"
        );
        books.forEach(System.out::println);
    }
}
