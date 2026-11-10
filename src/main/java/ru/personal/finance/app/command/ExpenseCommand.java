package ru.personal.finance.app.command;

import ru.personal.finance.domain.Category;
import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.StatisticService;
import ru.personal.finance.services.WalletService;

public class ExpenseCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;
    private final StatisticService statisticService;

    public ExpenseCommand(
            AuthService authService,
            WalletService walletService,
            StatisticService statisticService
    ) {
        this.authService = authService;
        this.walletService = walletService;
        this.statisticService = statisticService;
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

        String categoryName = args[0];
        walletService.addExpense(user, categoryName, amount);
        System.out.printf("Расход %.1f по категории %s добавлен%n", amount, categoryName);

        Wallet wallet = walletService.getWallet(user);
        Category category = wallet.getCategory(categoryName);

        if (statisticService.isBudgetExceeded(category)) {
            System.out.printf("Превышен лимит бюджета по категории %s%n", categoryName);
        }

        if (statisticService.isExpenseExceedIncome(wallet)) {
            System.out.println("Расходы превысили доходы");
        }
    }

    @Override
    public String getDescription() {
        return "добавить расход - expense <category> <amount>";
    }

    private String getMessageHowUseCommand() {
        return "Ошибка при вводе. Нужно использовать: expense <category> <amount>";
    }
}
