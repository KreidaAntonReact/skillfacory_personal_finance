package ru.personal.finance.cli;

import ru.personal.finance.cli.command.Command;
import ru.personal.finance.cli.command.ExitCommand;
import ru.personal.finance.cli.command.HelpCommand;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final LinkedHashMap<String, Command> commands = new LinkedHashMap<String, Command>();
    private boolean isRun = true;

    public App() {
        commands.put("help", new HelpCommand(commands));
        commands.put("exit", new ExitCommand(this::stop));
    }

    public void stop() {
        this.isRun = false;
    }

    public void run() {
        System.out.println("Привет! Для того чтобы начать использовать приложение \n"
                + "ознакомся с перечнем команд.");
        commands.get("help").execute();

        while (isRun) {
            System.out.print("> ");

            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue;
            }

            String commandText = parseInputText(input);
            Command command = commands.get(commandText);

            if (command == null) {
                System.out.print("Вы ввели неизвестную команнду");
                continue;
            }

            command.execute();
        }
    }


    private String parseInputText(String inputText) {
        return inputText.split(" ")[0].trim().toLowerCase();
    }
}
