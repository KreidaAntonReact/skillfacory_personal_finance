package ru.personal.finance.app;

import ru.personal.finance.app.command.AddBudgetCommand;
import ru.personal.finance.app.command.CategoriesCommand;
import ru.personal.finance.app.command.CategoryCommand;
import ru.personal.finance.app.command.Command;
import ru.personal.finance.app.command.CreateCommand;
import ru.personal.finance.app.command.ExitCommand;
import ru.personal.finance.app.command.HelpCommand;
import ru.personal.finance.app.command.ExpenseCommand;
import ru.personal.finance.app.command.IncomeCommand;
import ru.personal.finance.app.command.LoginCommand;
import ru.personal.finance.app.command.LogoutCommand;
import ru.personal.finance.repositories.UserRepository;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.UserService;
import ru.personal.finance.services.WalletService;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final LinkedHashMap<String, Command> commands = new LinkedHashMap<String, Command>();
    private boolean isRun = true;

    public App() {
        UserRepository userRepository = new UserRepository();
        WalletService walletService = new WalletService();
        UserService userService = new UserService(userRepository, walletService);
        AuthService authService = new AuthService(userService);

        commands.put("help", new HelpCommand(commands));
        commands.put("create", new CreateCommand(userService));
        commands.put("login", new LoginCommand(authService));
        commands.put("logout", new LogoutCommand(authService));
        commands.put("category", new CategoryCommand(authService, walletService));
        commands.put("categories", new CategoriesCommand(authService, walletService));
        commands.put("addBudget", new AddBudgetCommand(authService, walletService));
        commands.put("income", new IncomeCommand(authService, walletService));
        commands.put("expense", new ExpenseCommand(authService, walletService));
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
            System.out.flush();

            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue;
            }

            String commandText = getCommandFromInput(input);
            Command command = commands.get(commandText);

            if (command == null) {
                System.out.println("Вы ввели неизвестную команду");
                continue;
            }

            String[] args = this.getArgFromInput(input);

            try {
                command.execute(args);
            } catch (Exception e) {
                this.logError(e);
            }
        }
    }

    private String getCommandFromInput(String input) {
        return input.trim().toLowerCase().split("\\s+")[0];
    }

    private String[] getArgFromInput(String input) {
        String[] args = input.trim().toLowerCase().split("\\s+");

        if (args.length <= 1) {
            return new String[0];
        }

        Stream<String> stream = Arrays.stream(args);

        return stream.skip(1).toArray(String[]::new);
    }

    private void logError(Exception error) {
        System.err.println(error.getMessage());
        System.err.flush();
    }
}
