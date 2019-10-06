package dev.muskrat.library.menu.commands;

import dev.muskrat.library.menu.CommandMenu;
import org.springframework.stereotype.Component;

@Component
public class UserCommand extends CommandMenu {

    public UserCommand() {
        super("user");
    }

    @Override
    public void run(String[] args) {

    }
}
