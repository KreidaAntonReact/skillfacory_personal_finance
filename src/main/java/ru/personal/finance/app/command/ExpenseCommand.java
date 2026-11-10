package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

public class ExpenseCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public ExpenseCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException(getMessageHowUseCommand());
        }

        User user = authService.requireCurrentUser();
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Сумма должна быть числом!");
        }

        walletService.addExpense(user, args[0], amount);
        System.out.printf("Расход %.1f по категории %s добавлен%n", amount, args[0]);
    }

    @Override
    public String getDescription() {
        return "добавить расход - expense <category> <amount>";
    }

    private String getMessageHowUseCommand() {
        return "Ошибка при вводе. Нужно использовать: expense <category> <amount>";
    }
}
