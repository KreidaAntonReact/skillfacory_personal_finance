package ru.personal.finance.app.command;

public interface Command {
    void execute(String... args);

    String getDescription();
}
