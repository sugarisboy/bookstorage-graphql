package dev.muskrat.library.menu.commands;

import dev.muskrat.library.menu.CommandMenu;
import org.springframework.stereotype.Component;

@Component
public class BookCommand extends CommandMenu {

    public BookCommand() {
        super("book");
    }

    @Override
    public void run(String[] args) {
        
    }
}
