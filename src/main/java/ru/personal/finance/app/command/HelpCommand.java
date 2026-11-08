package ru.personal.finance.app.command;

import java.util.LinkedHashMap;
import java.util.Map;

public class HelpCommand implements Command {
    private final LinkedHashMap<String, Command> commands;

    public HelpCommand(LinkedHashMap<String, Command> commands) {
        this.commands = commands;
    }


    @Override
    public void execute(String... args) {
        System.out.println("Вот перечень допустимых комманд:");

        StringBuilder messageWithOptions = new StringBuilder(new StringBuilder());

        int order = 1;

        for(Map.Entry<String, Command> entry : commands.entrySet()) {
            String partMessage = String.format("%d. %s - %s", order, entry.getKey(), entry.getValue().getDescription());
            order++;

            messageWithOptions.append(partMessage).append("\n");
        }

        System.out.println(messageWithOptions);
    }

    @Override
    public String getDescription() {
        return "выводит перечень комманд в приложении";
    }
}
