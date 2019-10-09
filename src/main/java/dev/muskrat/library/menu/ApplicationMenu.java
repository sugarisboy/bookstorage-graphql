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
        System.out.println("Приложение запущено. Используйте help для большей информации.");
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
