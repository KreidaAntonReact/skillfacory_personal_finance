package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.StatisticService;
import ru.personal.finance.services.WalletService;

public class IncomeCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;
    private final StatisticService statisticService;

    public IncomeCommand(
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

        walletService.addIncome(user, args[0], amount);
        System.out.printf("Доход %.1f по категории %s добавлен%n", amount, args[0]);

        Wallet wallet = walletService.getWallet(user);

        if (statisticService.isExpenseExceedIncome(wallet)) {
            System.out.println("Расходы превысили доходы");
        }
    }

    @Override
    public String getDescription() {
        return "добавить доход - income <category> <amount>";
    }

    private String getMessageHowUseCommand() {
        return "Ошибка при вводе. Нужно использовать: income <category> <amount>";
    }
}
