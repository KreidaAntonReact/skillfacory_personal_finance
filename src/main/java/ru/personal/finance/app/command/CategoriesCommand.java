package ru.personal.finance.app.command;

import ru.personal.finance.domain.User;
import ru.personal.finance.domain.Wallet;
import ru.personal.finance.services.AuthService;
import ru.personal.finance.services.WalletService;

import java.util.Set;

public class CategoriesCommand implements Command {
    private final AuthService authService;
    private final WalletService walletService;

    public CategoriesCommand(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    @Override
    public void execute(String... args) {
        User user = authService.requireCurrentUser();
        Wallet wallet = walletService.getWallet(user);
        Set<String> categories = wallet.getCategories();

        if (categories.isEmpty()) {
            System.out.println("Категории пока не созданы");
            return;
        }

        System.out.println("Категории кошелька:");
        int order = 1;
        for (String category : categories) {
            System.out.printf("%d. %s%n", order, category);
            order++;
        }
    }

    @Override
    public String getDescription() {
        return "показать категории кошелька - categories";
    }
}
