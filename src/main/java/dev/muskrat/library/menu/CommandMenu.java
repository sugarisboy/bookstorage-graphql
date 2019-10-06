package dev.muskrat.library.menu;

import dev.muskrat.library.exception.BadRequestException;
import lombok.Getter;


public abstract class CommandMenu {

    @Getter
    private final String cmd;

    protected CommandMenu(String cmd) {
        this.cmd = cmd;
    }

    public abstract void run(String[] args) throws BadRequestException;
}
