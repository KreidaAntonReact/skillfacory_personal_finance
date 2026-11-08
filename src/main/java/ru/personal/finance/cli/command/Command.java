package ru.personal.finance.cli.command;

public interface Command {
    public void execute(String... args);

    public String getDescription();
}
