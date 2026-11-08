package ru.personal.finance.cli.command;

public class ExitCommand implements Command {
    private final Runnable onExit;

    public  ExitCommand(Runnable onExit) {
        this.onExit = onExit;
    }


    @Override
    public void execute(String... args) {
        System.out.println("До свидания!");
        onExit.run();
    }

    @Override
    public String getDescription() {
        return "закончить работу с программой";
    }
}
