package dev.muskrat.library.menu;

import dev.muskrat.library.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ApplicationMenu {

    private final List<CommandMenu> cmds;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        Scanner scan = new Scanner(System.in);

        printMenu();
        while (true) {
            String line = scan.nextLine();
            if (command(line))
                break;
        }
    }

    private void printMenu() {
        System.out.println("-----[ B O O K   S T O R A G E ]-----");
        System.out.println();
        System.out.println("book add <title> <writer> <genre> <age limit> <count>");
        System.out.println("book delete <book id>");
        System.out.println("book find genre <user id> <genre>");
        System.out.println("book find writer <user id> <writer>");
        System.out.println();
        System.out.println("user add <first name> <second name> <third name> <day> <month> <year>");
        System.out.println("user delete <user id>");
        System.out.println("user addbook <book id>");
        System.out.println("user takebook <book id>");
        System.out.println();
        System.out.println("books <user id> sort writer");
        System.out.println("books <user id> sort title");
        System.out.println("users");
    }

    private boolean command(String line) {
        String[] args = line.split(" ");
        String sendCmd = args[0];

        if (sendCmd.equalsIgnoreCase("exit"))
            return true;

        for (CommandMenu cmd : cmds) {
            if (cmd.getCmd().equalsIgnoreCase(sendCmd)) {

                try {
                    cmd.run(args);
                } catch (BadRequestException e) {
                    System.out.println("[EXCEPTION]: " + e.getMessage());
                }
                return false;
            }
        }

        return false;
    }
}
