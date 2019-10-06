package dev.muskrat.library.menu.commands;

import dev.muskrat.library.menu.CommandMenu;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class UsersCommand extends CommandMenu {

    @Autowired
    private UserRepository userRepository;

    public UsersCommand() {
        super("users");
    }

    @Override
    public void run(String[] args) {
        System.out.printf("%3s %12s %12s %16s %15s\n",
            "id",
            "name",
            "lastName",
            "thirdName",
            "birthday"
        );
        userRepository.findAll().forEach(System.out::println);
    }
}
