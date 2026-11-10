package ru.personal.finance.app.command;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

import java.util.Collection;

public class BudgetCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public BudgetCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        User user = authService.requireCurrentUser();

        if (args.length == 0) {
            showBudgets(user);
            return;
        }

        if ("add".equalsIgnoreCase(args[0])) {
            addBudget(user, args);
            return;
        }

        if (args.length == 1) {
            showBudget(user, args[0]);
            return;
        }

        throw new IllegalArgumentException(getMessageHowUseCommand());
    }

    @Override
    public String getDescription() {
        return "бюджет категории - budget add <category> <amount> | budget <category> | budget";
    }

    private void addBudget(User user, String... args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Использование: budget add <category> <amount>");
        }

        double budget;

        try {
            budget = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Бюджет должен быть числом!");
        }

        walletService.addBudget(user, args[1], budget);
        System.out.printf("Бюджет категории %s установлен: %.1f%n", args[1], budget);
    }

    private void showBudget(User user, String categoryName) {
        Category category = walletService.getCategoryWithBudget(user, categoryName);
        System.out.printf(
                "%s: %.1f, Оставшийся бюджет: %.1f%n",
                category.getName(),
                category.getBudget().get(),
                category.getRemainingBudget()
        );
    }

    private void showBudgets(User user) {
        Wallet wallet = walletService.getWallet(user);
        Collection<Category> categories = wallet.getCategories();

        StringBuilder message = new StringBuilder();
        int order = 1;

        for (Category category : categories) {
            if (category.getBudget().isEmpty()) {
                continue;
            }

            message.append(String.format(
                    "%d. %s: %.1f, Оставшийся бюджет: %.1f%n",
                    order,
                    category.getName(),
                    category.getBudget().get(),
                    category.getRemainingBudget()
            ));
            order++;
        }

        if (message.isEmpty()) {
            System.out.println("Бюджеты пока не установлены");
            return;
        }

        System.out.println("Бюджеты:");
        System.out.print(message);
    }

    private String getMessageHowUseCommand() {
        return "Использование: budget add <category> <amount> | budget <category> | budget";
    }
}
