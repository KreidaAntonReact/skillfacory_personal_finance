package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

public class CategoryCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public CategoryCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Использование: category <name> <budget>");
        }

        User user = authService.requireCurrentUser();
        double budget;

        try {
            budget = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Бюджет должен быть числом!");
        }

        walletService.addCategory(user, args[0], budget);
        System.out.printf("Категория %s успешно создана с бюджетом %.1f%n", args[0], budget);
    }

    @Override
    public String getDescription() {
        return "создать категорию - category <name> <budget>";
    }
}
